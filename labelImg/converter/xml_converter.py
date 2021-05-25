import os
import xml.etree.ElementTree as ET


def formatNumber(num):
    formatLen = 4
    return "0"*(formatLen - len(num)) + num


if __name__ == "__main__":

    path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/pathfinder/labelImg/box/"
    # path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/testxml/"
    newFilePath = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/sample_jpg/"
    newFileFolder = "sample_jpg"

    for fileName in os.listdir(path):

        if fileName[:3] == "img":
            # rename

            fileInfo = fileName.split("(")[1].split(").")

            fileNum = fileInfo[0]
            fileExt = fileInfo[1]

            formatted = formatNumber(fileNum)

            newXmlName = path + formatted + "." + fileExt

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

            print(fileName + " => " +
                  formatted + "." + fileExt)
