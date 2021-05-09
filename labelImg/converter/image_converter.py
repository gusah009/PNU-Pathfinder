import os
from PIL import Image


def formatNumber(num):
    formatLen = 4
    return "0"*(formatLen - len(num)) + num


if __name__ == "__main__":
    # path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/sampleIMG/"

    path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/sampleIMG_2/"
    newPath = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/sample_jpg/"

    for fileName in os.listdir(path):

        # s = "img (99).png"
        if fileName[:3] == "img":
            fileInfo = fileName.split("(")[1].split(").")

            fileNum = fileInfo[0]
            fileExt = fileInfo[1]

            newExt = "jpg"
            # newName = path + formatNumber(fileNum) + "." + fileExt
            newName = newPath + formatNumber(fileNum) + "." + newExt

            # when just change file name
            # os.rename(path + fileName, newName)

            # when convert image extension, don't use upper line with this one
            im = Image.open(path + fileName)
            im = im.convert("RGB")
            im.save(newName)

            print(fileName + " => " +
                  formatNumber(fileNum) + "." + newExt)
