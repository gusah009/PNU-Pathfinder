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

startPoint1 = "https://map.naver.com/v5/?c=14369591.9218964,4195404.0717501,16,0,0,0,dh&p=ImO6G5htZm11gggAlLO8dQ,-62.44,6.24,80,Float"
startPoint2 = "https://map.naver.com/v5/?c=14369528.3139394,4195213.3590958,16,0,0,0,dh&p=7WD2t9pjQRsHknwHQi29Gw,-3.82,6.15,80,Float"

index = 0

delay = 30 # seconds

log = {}

DIR = './IMG/'

log_file = open("url_log_data.txt", "a")

if platform.system() == 'Darwin': # MAC
  driver = webdriver.Chrome(executable_path='./chromedriver 2')
elif platform.system() == 'Windows':
  driver = webdriver.Chrome(executable_path='chromedriver')
driver.maximize_window()
driver.get(url=startPoint2)

def removeElement():
  try:
    script = """
      btn_area = document.getElementsByClassName('btn_area');
      btn_close = document.getElementsByClassName('btn_close');
      btn_area[1].style.display = 'none';
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
  pyautogui.moveTo(10, 10)
  sleep(0.7)

def goBack():
  for _ in range(4):
    moveLeftCamera()
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
  sleep(0.1)
  removeElement()
  faceNorth()
  moveLeftCamera()
  moveLeftCamera()
  goFront()
  global log
  # 정문
  log['14369591.9218964,4195404.0717501'] = True
  # 학생회관
  log['14369487.6377975,4195520.9894736'] = True
  # 인문관앞
  log['14369323.8868265,4195514.2842857'] = True
  # 인문관 - 운죽정 사이
  log['14369271.2772352,4195393.6733692'] = True
  # 인문관 - 운죽정 사이
  log['14369271.2772352,4195393.6733692'] = True
  # 운죽정 뒤편
  log['14369281.8414548,4195292.3067035'] = True
  # 제도관 뒤편
  log['14369410.7828210,4195233.9919912'] = True

  with open('url_log_data.txt', "r") as logs_url:
    for log_url in logs_url:
      loc_start = log_url.find('c=') + 2
      loc_end = log_url.find('&') - 12
      log[log_url[loc_start:loc_end]] = True

  # print(log)

def saveScreenShot(version):
  save_url = DIR + version +".png"
  driver.save_screenshot(save_url)
  

def wait_for_correct_current_url(prev, start, end):
  start_time = time()
  while True:
    curr = driver.current_url[start:end]
    if curr != prev:
      return True
    if time() - start_time > 3:
      return False

def backCurrUrl(curr_url, angle):
  start = curr_url.find('&p=') + 25
  end = curr_url.find('Float')
  curr_url = curr_url[:start] + angle + curr_url[end:]
  driver.get(curr_url)
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'btn_area'))
  )
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'panorama_location_area'))
  )
  sleep(0.1)
  removeElement()
  return curr_url

def findAngle():
  curr_url = driver.current_url
  start = curr_url.find('&p=') + 25
  end = curr_url.find('Float')
  return (curr_url[start:end], start, end)

def action(depth):
  curr_url =  driver.current_url
  loc_start = curr_url.find('c=') + 2
  loc_end = curr_url.find('&') - 12
  location = curr_url[loc_start : loc_end]
  if location in log:
    return location
  else:
    log[location] = True
    log_file.writelines(curr_url + "\n")

  for i in range(8):
    angle, ang_start, ang_end = findAngle()
    version = str(depth) + '_' + str(i) + '_' + location
    saveScreenShot(version)
    moveLeftCamera()
    wait_for_correct_current_url(curr_url, ang_start, ang_end)

  for i in range(8):
    angle, ang_start, ang_end = findAngle()
    goFront()
    canMove = wait_for_correct_current_url(location, loc_start, loc_end)
    if canMove:
      action(depth + 1)
      curr_url = backCurrUrl(curr_url, angle)
    moveLeftCamera()
    wait_for_correct_current_url(curr_url, ang_start, ang_end)

def main():
  initCrawler()

  try:
    action(1000)
  except TimeoutException:
    print("Loading took too much time!")

main()