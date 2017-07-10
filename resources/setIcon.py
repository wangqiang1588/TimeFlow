# coding=utf-8
from PIL import Image  
import shutil  
import os  

fileDir = os.path.dirname(os.path.realpath(__file__))
raw_image = os.path.join(fileDir,"Icon_green.png")
store_path = os.path.join(fileDir,"..","app","src","main","res")

mdpi_path = os.path.join(store_path,"mipmap-mdpi")
ic_launcher_mdpi = os.path.join(mdpi_path,"ic_launcher.png")
ic_launcher_round_mdpi = os.path.join(mdpi_path,"ic_launcher_round.png")
pixes_mdpi = 48

hdpi_path = os.path.join(store_path,"mipmap-hdpi")
ic_launcher_hdpi = os.path.join(hdpi_path,"ic_launcher.png")
ic_launcher_round_hdpi = os.path.join(hdpi_path,"ic_launcher_round.png")
pixes_hdpi = 72

xhdpi_path = os.path.join(store_path,"mipmap-xhdpi")
ic_launcher_xhdpi = os.path.join(xhdpi_path,"ic_launcher.png")
ic_launcher_round_xhdpi = os.path.join(xhdpi_path,"ic_launcher_round.png")
pixes_xhdpi = 96

xxhdpi_path = os.path.join(store_path,"mipmap-xxhdpi")
ic_launcher_xxhdpi = os.path.join(xxhdpi_path,"ic_launcher.png")
ic_launcher_round_xxhdpi = os.path.join(xxhdpi_path,"ic_launcher_round.png")
pixes_xxhdpi = 144

xxxhdpi_path = os.path.join(store_path,"mipmap-xxxhdpi")
ic_launcher_xxxhdpi = os.path.join(xxxhdpi_path,"ic_launcher.png")
ic_launcher_round_xxxhdpi = os.path.join(xxxhdpi_path,"ic_launcher_round.png")
pixes_xxxhdpi = 192


if __name__ == "__main__":
    im = Image.open(raw_image)  

    out = im.resize((pixes_mdpi, pixes_mdpi), Image.ANTIALIAS)
    out.save(ic_launcher_mdpi)
    out.save(ic_launcher_round_mdpi)

    out = im.resize((pixes_hdpi, pixes_hdpi), Image.ANTIALIAS)
    out.save(ic_launcher_hdpi)
    out.save(ic_launcher_round_hdpi) 

    out = im.resize((pixes_xhdpi, pixes_xhdpi), Image.ANTIALIAS)
    out.save(ic_launcher_xhdpi)
    out.save(ic_launcher_round_xhdpi)

    out = im.resize((pixes_xxhdpi, pixes_xxhdpi), Image.ANTIALIAS)
    out.save(ic_launcher_xxhdpi)
    out.save(ic_launcher_round_xxhdpi) 

    out = im.resize((pixes_xxxhdpi, pixes_xxxhdpi), Image.ANTIALIAS)
    out.save(ic_launcher_xxxhdpi)
    out.save(ic_launcher_round_xxxhdpi)            

