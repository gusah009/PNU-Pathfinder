# -*- coding: utf-8 -*-

import selenium
from selenium import webdriver
# from selenium.webdriver import ActionChains

# from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By

from selenium.webdriver.support import expected_conditions as EC
# from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.common.exceptions import TimeoutException
from time import sleep
from time import time



import platform

import pyautogui

startURL = 'https://map.naver.com/v5/?c=14369619.0924437,4195394.3941742,15,0,0,0,dha&p=QT1tMDk_XIdEotJVEo6ClQ,-90,0,80,Float'

index = 0

delay = 30 # seconds

log = []

DIR = './IMG/'

log_file = open("url_log_data.txt", "a")

if platform.system() == 'Darwin': # MAC
  driver = webdriver.Chrome(executable_path='./chromedriver 2')
elif platform.system() == 'Windows':
  driver = webdriver.Chrome(executable_path='chromedriver')
driver.maximize_window()
driver.get(url=startURL)

def removeElement():
  try:
    script = """
      btn_area = document.getElementsByClassName('btn_area');
      btn_close = document.getElementsByClassName('btn_close');
      btn_area[2].style.display = 'none';
      btn_close[0].style.display = 'none';
      bottom_area = document.getElementsByClassName('panorama_location_area')[0].parentNode.parentNode.childNodes;
      console.log(bottom_area);
      bottom_area[4].remove();
      bottom_area[3].remove();
      bottom_area[2].remove();
      bottom_area[1].remove();
    """
    driver.execute_script(script)
  except:
    print('removeElement Error\n')

def faceNorth():
  try:
    WebDriverWait(driver, delay).until(
      EC.presence_of_element_located((By.CLASS_NAME , 'btn_compass'))
    )
    script = """
      btn_compass = document.getElementsByClassName('btn_compass');
      btn_compass[0].click();
    """
    driver.execute_script(script)
    sleep(0.7)
  except:
    print('faceNorth Error\n')

def moveUpCamera():
  script = """
    btn_top = document.getElementsByClassName('btn_top');
    btn_top[0].click();
  """
  driver.execute_script(script)
  sleep(0.7)
  
def moveLeftCamera():
  script = """
    btn_left = document.getElementsByClassName('btn_left');
    btn_left[0].click();
  """
  driver.execute_script(script)
  sleep(0.7)
  
def goFront():
  # 모니터 전체 사이즈
  # 제 컴퓨터에 최적화되어있습니다!!
  width, height = pyautogui.size()
  pyautogui.moveTo(width / 2, height * 0.94)
  sleep(0.02)
  pyautogui.click()
  pyautogui.moveTo(1, 1)
  sleep(0.7)

def goBack():
  for _ in range(4):
    moveLeftCamera()
  print('-------')
  goFront()

def lookBack():
  for _ in range(4):
    moveLeftCamera()
    sleep(0.1)

def initCrawler():
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'btn_area'))
  )
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'panorama_location_area'))
  )
  sleep(1)
  removeElement()
  faceNorth()
  moveLeftCamera()
  moveLeftCamera()
  goFront()
  log.append('14369454.2751015,4195763.4417014,16,0,0,0,dha')
  log.append('14369445.9148015,4195933.0363579,16,0,0,0,dha')
  log.append('14368732.9006470,4195452.9162740,16,0,0,0,dha')
  log.append('14369437.5545015,4195172.2490607,16,0,0,0,dha')

def saveScreenShot(version):
  save_url = DIR + version +".png"
  driver.save_screenshot(save_url)
  

def wait_for_correct_current_url(prev_loc, start, end):
  start_time = time()
  while True:
    curr_loc = driver.current_url[start:end]
    if curr_loc != prev_loc:
      break
    if time() - start_time > 5:
      moveLeftCamera()
      goFront()
      break
  # WebDriverWait(driver, delay).until(lambda driver: driver.current_url == prev_url)

def action():
  # print(log)
  curr_url =  driver.current_url
  start = curr_url.find('c=')
  end = curr_url.find('&')
  location = curr_url[start + 2 : end]
  if location in log:
    return location
  else:
    global index
    index += 1
    log.append(location)
    log_file.writelines(location + "\n")

  for i in range(3):
    for j in range(8):
      version = str(index) + '_' + str(i) + '_' + str(j) + '_' + location
      goFront()
      wait_for_correct_current_url(location, start + 2, end)
      prev_loc = action()
      goBack()
      wait_for_correct_current_url(prev_loc, start + 2, end)
      saveScreenShot(version)
      # lookBack()
      moveLeftCamera()
    moveUpCamera()

def main():
  initCrawler()

  try:
    action()
  except TimeoutException:
    print("Loading took too much time!")

main()