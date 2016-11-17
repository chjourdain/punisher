# punisher-balancer
This is the API which take a name and shoot the guy by sendind command to punisher API

# punisher
This is the API directly connected to usb Thunder driver.
## punisher docker : 
You have to get the .war . Run this command in the dockerfile folder to build the image : 
```bash
cp ../../punisher/target/punisher-shooter.war .
# build the image
docker build -t punisher-shooter .
# then run the punisher-shooter
docker run -it --name punisher-shooter-ctn -p 8080:8080 --privileged -v /dev/bus/usb:/dev/bus/usb punisher-shooter
```
When you run it, the retaliation device should park to it's lefter position.
If not, you have a problem while communicate througt usb.

# USB permissions
If you have trouble to connect the usb device under linux

Create and aedit the file : 
```bash
sudo vi /etc/udev/rules.d/99-garmin.rules
```
Add the following to it :
```bash
SUBSYSTEM=="usb", ATTR{idVendor}=="2123", ATTR{idProduct}=="1010", MODE="666"
```
Unplug the missile launcher
Restart udev with the following command
```bash
sudo udevadm trigger
```
Plug back the missile launcher

# Jenkins
To trigger fire on build failure, use 'Hudson Post Build Task' plugin
This plugin scan the output of jenkins console, and you can define some log
that, if they appear, trigger the shell command.
So you may put 'BUILD FAILURE' for a maven build or just 'failure' for any kind of failure.

About the command line to execute, if you use the github plugin you can use the following command line with the correct location of the balancer api
```bash
var=$(git log -n 1 --pretty=format:%aN) && curl http://localhost:8084/punisher-balancer/api/fire/$var 
```

# Interface
A script is provided so you can add, remove, update or delete programmers from a terminal. Make sure the balancer is running.
The errors are not well handled yet. You might need to edit this script to set the address of balancer API correctly, feel free.
Some commands need the jsawk program (json parsing) to run correctly.
```bash
# Linux
curl -L http://github.com/micha/jsawk/raw/master/jsawk > jsawk
chmod 755 jsawk && mv jsawk ~/bin/

# OSX
brew install jsawk
```

