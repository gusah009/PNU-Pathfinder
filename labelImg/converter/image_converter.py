import os
from PIL import Image


def formatNumber(num):
    formatLen = 4
    return "0"*(formatLen - len(num)) + num


if __name__ == "__main__":

    startNum = 841

    # path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/sampleIMG/
    # path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/sampleIMG_2/"
    path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/sampleIMG_3/"

    newPath = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/sample_jpg/"

    # path = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/testimg/" # for test
    # newPath = "/mnt/c/shasuri/assignment/3g_1s/sw_arch_exp/sample_images/testimg/testjpg/" # for test

    for fileSeq, fileName in enumerate(os.listdir(path)):

        # s = "img (99).png"
        # s = "158_0_14369088.8105600,4195296.4592317.png"

        # print("seq : " + str(fileName))
        # print("name : " + fileSeq)

        '''
        if fileName[:3] == "img":
            fileInfo = fileName.split("(")[1].split(").")

            fileNum = fileInfo[0]
        '''

        fileNum = str(startNum + fileSeq)

        fileExt = "png"
        newExt = "jpg"

        # newName = path + formatNumber(fileNum) + "." + fileExt
        newName = newPath + formatNumber(fileNum) + "." + newExt

        # when just change file name
        # os.rename(path + fileName, newName)

        # when convert image extension, don't use upper line with this one
        if fileName[-4:] == '.' + fileExt:
            im = Image.open(path + fileName)
            im = im.convert("RGB")
            im.save(newName)

            print(fileName + " => " +
                  formatNumber(fileNum) + "." + newExt)
