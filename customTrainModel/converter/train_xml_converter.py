import os
import xml.etree.ElementTree as ET
import random
import shutil

def formatNumber(num):
    formatLen = 4
    return "0"*(formatLen - len(num)) + num


if __name__ == "__main__":

    path = "./dataset/"
    # path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/testxml/"
    newFilePath = "../images/train/"
    newFileFolder = "train"
    newFilePath2 = "../images/test/"
    newFileFolder2 = "test"

    i = 1
    j = 1
    for fileName in os.listdir(path):
        if random.random() < 0.7:
            fileNum = i
            i += 1
            fileExt = 'xml'

            formatted = formatNumber(str(fileNum))

            newXmlName = newFilePath + formatted + "." + fileExt

            os.rename(path + fileName, newXmlName)

            # edit XML

            newFileName = formatted + ".jpg"
            tree = ET.parse(newXmlName)
            root = tree.getroot()

            # oldPath = root[2].text.split("img")[0]

            root[0].text = newFileFolder
            root[1].text = newFileName
            root[2].text = newFilePath + newFileName

            tree.write(newXmlName)

            shutil.copy(newFileName, newFilePath + newFileName)
            print(fileName + " => " +
                    formatted + "." + fileExt)
        else:
            fileNum = j
            j += 1
            fileExt = 'xml'

            formatted = formatNumber(str(fileNum))

            newXmlName = newFilePath2 + formatted + "." + fileExt

            os.rename(path + fileName, newXmlName)

            # edit XML

            newFileName = formatted + ".jpg"
            tree = ET.parse(newXmlName)
            root = tree.getroot()

            # oldPath = root[2].text.split("img")[0]

            root[0].text = newFileFolder2
            root[1].text = newFileName
            root[2].text = newFilePath2 + newFileName

            tree.write(newXmlName)

            shutil.copy(newFileName, newFilePath2 + newFileName)
            print(fileName + " => " +
                    formatted + "." + fileExt)
