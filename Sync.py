import threading
import os

path = os.path.dirname(os.path.abspath('__file__'))
path = path.replace(" ", "\ ")

os.system("rm -rf " + path + "/Server/app/interface")
os.system("ln -s " + path + "/Client/src/main/scala/interface/ " + path + "/Server/app/interface")
os.system("rm " + path + "/Server/public/javascripts/maliki-fastopt.js")
os.system("ln -s " + path + "/Client/target/scala-2.11/maliki-fastopt.js " + path + "/Server/public/javascripts/maliki-fastopt.js")
os.system("rm " + path + "/Server/public/javascripts/maliki-fastopt.js.map")
os.system("ln -s " + path + "/Client/target/scala-2.11/maliki-fastopt.js.map " + path + "/Server/public/javascripts/maliki-fastopt.js.map")