 
#!/bin/bash
binPath="../bin"
libPath="../lib"
projName="InstantMessenger_WebService"
warName="InstantMessenger_WebService"
apachePath="/usr/local/apache-tomcat9"
url="http://localhost:8080/$warName/rest/users/usernames"
srcPath="../$projName/src"

echo " removing ./bin folder"
    
rm -rf $binPath

echo " creating ./bin folder"

mkdir -p $binPath

echo " copying WebContent in temp folder"

cp -R ../$projName/WebContent $binPath/tmp

if [ $? -ne 0 ]; then
    echo " error copying WebContent in temp folder"
	exit -1
fi

echo " compiling classes"
javac -sourcepath $srcPath $srcPath/data/*.java $srcPath/dao/*.java $srcPath/resources/*.java $srcPath/controller/*.java -cp "../$projName/WebContent/WEB-INF/lib/*:$srcPath/*"
if [ $? -ne 0 ]; then
    echo " error compiling classes"
	exit -1
fi


echo " copying classes folder in temp folder"

cp -R ../$projName/build/classes $binPath/tmp/WEB-INF/

if [ $? -ne 0 ]; then
    echo " error copying classes folder in temp folder"
	exit -1
fi

echo " generating .war file"

cd $binPath/tmp

jar -cvf $warName.war .

if [ $? -ne 0 ]; then
    echo " error creating .war file"
	exit -1
fi

mv $warName.war ../$binPath/

    echo " successfully generated and moved .war file"
    sleep 1

cd ..

rm -rf $binPath/tmp

if [ $? -ne 0 ]; then
    echo " error deleting temp folder"
	exit -1
fi

    echo " successfully deleted temp folder"

echo " copying .war file in apache tomcat webapps"

cp $binPath/$warName.war $apachePath/webapps

if [ $? -ne 0 ]; then
    echo " error copying .war file"
	exit -1
fi

    echo " successfully copied .war file"
    
echo " starting apache tomcat"

cd /
cd usr/local
cd apache-tomcat9
cd bin

sudo sh ./startup.sh

if [ $? -ne 0 ]; then
    echo " error starting apache tomcat"
	exit -1
fi

echo " successfully started apache tomcat"

echo "  project is served on:"
echo "     $url"
