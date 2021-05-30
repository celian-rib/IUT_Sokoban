headerFile="header.txt"
headerLineCount=10
fileExt=".java"

function main () {
    echo "----------------------------------------------------"
    echo
    echo "Header generator (Made by Célian Riboulet)"
    echo
    echo "----------------------------------------------------"
    echo "header is $headerFile"
    echo "/!\ should have $headerLineCount lines"
    echo "if not change this parameter in the script"
    echo "----------------------------------------------------"
    echo "This script has duplicates security"
    echo "You should not mess up your files thanks to that"
    echo "But we never know..."
    echo "----------------------------------------------------"
    echo
    echo "Generate [g] or clear [c] headers from all $fileExt files :"

    read input

    if test "$input" = "g"; then
        echo "Adding this header to all $fileExt file in the project !"

        cat $headerFile
        echo "Adding headers ..."

        for file in $(find ./ -name "*$fileExt"); do
            if test "$(cat $file | head -n 1)" = "$(cat header.txt | head -n 1)"; then
                echo "[!] Header already found in $file"
            else
                cat $headerFile | cat - $file > temp && mv temp $file
                echo "[OK] Adding in $file"
            fi
        done

    elif test "$input" = "c"; then

        echo "Removing all headers ..."

        for file in $(find ./ -name "*$fileExt"); do
            if test "$(cat $file | head -n 1)" = "$(cat header.txt | head -n 1)"; then
                echo "$(tail -n +$headerLineCount $file)" > $file
                echo "[OK] Removing in $file"
            else
                echo "[!] No header found in $file"
            fi
        done

    else
        echo
        echo "ERROR : unkown action..."
        main
    fi
}

main

echo
echo "done !"
