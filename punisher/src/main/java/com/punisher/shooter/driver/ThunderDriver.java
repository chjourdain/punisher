package com.punisher.shooter.driver;

import org.usb4java.*;

import java.nio.ByteBuffer;

public class ThunderDriver {
	private static final short VENDOR = (short) 0x2123;
	private static final short PRODUCT = (short) 0x1010;
	protected static final int ROTATE_TIME_X = 6600;
	protected static final int ROTATE_TIME_Y = 1000;
	private Context context;
	private DeviceHandle handle;
	private static ThunderDriver instance = new ThunderDriver();

	private ThunderDriver() {
		context = new Context();
		findDevice(VENDOR, PRODUCT);
		int r = LibUsb.detachKernelDriver(handle, 0);
		if (r != LibUsb.SUCCESS && r != LibUsb.ERROR_NOT_SUPPORTED && r != LibUsb.ERROR_NOT_FOUND) {
			throw new LibUsbException("Unable to detach kernel     driver", r);
		}
		int result = LibUsb.claimInterface(handle, 0);
		if (result != LibUsb.SUCCESS) {
			throw new LibUsbException("Unable to claim interface", result);
		}
		try {
			run(Command.PARK, 0);
		} catch (InterruptedException e) {
		}
	}

	public static ThunderDriver getInstance() {
		return instance;
	}

	public void execute(LowCommand e) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(8);
		buffer.put(e.getArray());
		buffer.rewind();
		int transfered = LibUsb.controlTransfer(handle, (byte) (LibUsb.REQUEST_TYPE_CLASS | LibUsb.RECIPIENT_INTERFACE),
				(byte) 0x09, (short) 256, (short) 0, buffer, 10000l);
		if (transfered < 0)
			throw new LibUsbException("Control transfer failed", transfered);
	}

	public void move(LowCommand e, int duration) {
		execute(e);
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		execute(LowCommand.STOP);
	}

	public synchronized void fire(int xRotation, int yRotation) throws InterruptedException {
		run(Command.RIGHT, xRotation * ROTATE_TIME_X / 345);
		run(Command.UP, yRotation * ROTATE_TIME_Y / 45);
		run(Command.FIRE, 1);
		run(Command.PARK, 0);
	}

	public void run(Command command, int value) throws InterruptedException {

		switch (command) {
		case DOWN:
			move(LowCommand.DOWN, value);
			break;
		case UP:
			move(LowCommand.UP, value);
			break;
		case RIGHT:
			move(LowCommand.RIGHT, value);
			break;
		case LEFT:
			move(LowCommand.LEFT, value);
			break;
		case PARK:
			move(LowCommand.DOWN, 2000);
			move(LowCommand.LEFT, 8000);
			break;
		case SLEEP:
			Thread.sleep(value);
		case STOP:
			break;
		case LED:
			if (value == 1) {
				execute(LowCommand.LED_ON);
			} else {
				execute(LowCommand.LED_OFF);
			}
			break;
		case FIRE:
			if (value > 0 && value < 5) {
				for (int i = 0; i < value; i++) {
					execute(LowCommand.FIRE);
					Thread.sleep(4500);
				}
			}
			break;
		default:
			break;
		}

		Thread.sleep(300);

	}

	public void close() {
		LibUsb.close(handle);
	}

	public void findDevice(short vendorId, short productId) {

		// Initialize the libusb context
		int result = LibUsb.init(context);
		if (result < 0) {
			throw new LibUsbException("Unable to initialize libusb", result);
		}

		// Read the USB device list
		DeviceList list = new DeviceList();
		result = LibUsb.getDeviceList(context, list);
		if (result < 0) {
			throw new LibUsbException("Unable to get device list", result);
		}

		try {
			// Iterate over all devices and list them
			for (Device device : list) {
				DeviceDescriptor descriptor = new DeviceDescriptor();
				result = LibUsb.getDeviceDescriptor(device, descriptor);
				if (result < 0) {
					throw new LibUsbException("Unable to read device descriptor", result);
				}
				if (result != LibUsb.SUCCESS) {
					throw new LibUsbException("Unable to read device descriptor", result);
				}
				if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) {
					getDeviceHandle(device);
				}

			}
		} finally {
			// Ensure the allocated device list is freed
			LibUsb.freeDeviceList(list, true);
		}
	}

	public void getDeviceHandle(Device device) {
		handle = new DeviceHandle();
		int result = LibUsb.open(device, handle);
		if (result != LibUsb.SUCCESS) {
			throw new LibUsbException("Unable to open USB device", result);
		}
	}
}
