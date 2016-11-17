#!/bin/bash
echo '
        __  
        \ \_____                     ######
        ###[==_____>              _/_ #####
        /_/      __              [.[.]-=###
                 \ \_____         /_    )#
              ###[==_____>        |__/  #
                 /_/               \___/
   ___ _   _ _  _ ___ ___ _  _     _  _ ___ ___
  | _ \ | | | \| |_ _/ __| || |___| || | __| _ \
  |  _/ |_| | .` || |\__ \ __ |___| __ | _||   /
  |_|  \___/|_|\_|___|___/_||_|   |_||_|___|_|_\
'

echo "  Welcome to Punisher Programmer Management Interface !"

URL="http://localhost:8081/api/programmer"
PS3='Please select an option: '
options=("list existing programmers" "add a programmer" "update a programmer" "delete a programmer" "exit")
select opt in "${options[@]}"
do
    case $opt in
        "list existing programmers")
            echo "Here is all existing programmer in our system :"
            resp=$(curl -s -H "Content-type: application/json" $URL | jsawk -n 'out(this.name)')
            echo $resp
            ;;
        "add a programmer")
            echo "name ?"
            read name
            echo "x ?"
            read x
            echo "y ?"
            read y
            echo "office number ?"
            read on
            curl -s -XPOST -H "Content-type: application/json" -d '{"name": "'$name'","coords": {"x": '$x',"y": '$y'}, "launcher": {"id": '$on'}}' $URL
            ;;
        "update a programmer")
            echo "name ?"
            read name
            echo "x ?"
            read x
            echo "y ?"
            read y
            echo "office number ?"
            read on
            curl -s -XUPDATE -H "Content-type: application/json" -d '{"name": "'$name'","coords": {"x": '$x',"y": '$y'}, "launcher": {"id": '$on'}}' $URL
            ;;
        "delete a programmer")
            echo "please enter a name"
            read name
            echo "delete '$name' ? (y/n)"
            read resp
            if [ $resp = "y" ];
            then
               curl -s -XDELETE -H "Content-type: application/json" $URL/$name
               echo "'$name' deleted"
            else
              echo "deletion cancelled."
            fi
            ;;
        "exit")
            echo "Than you for using Punisher Programmer Management Interface, come again !"
            break
            ;;
        *) echo invalid option;;
    esac
done
