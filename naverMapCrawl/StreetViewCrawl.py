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

import pyautogui

def removeElement():
  try:
    script = """
      btn_area = document.getElementsByClassName('btn_area');
      btn_close = document.getElementsByClassName('btn_close');
      btn_area[2].style.display = 'none';
      btn_close[0].style.display = 'none';
    """
    driver.execute_script(script)
  except:
    print('removeElement Error\n')

def faceNorth():
  try:
    element = WebDriverWait(driver, delay).until(
      EC.presence_of_element_located((By.CLASS_NAME , 'btn_compass'))
    )
    script = """
      btn_compass = document.getElementsByClassName('btn_compass');
      btn_compass[0].click();
    """
    driver.execute_script(script)
  except:
    print('faceNorth Error\n')

def initCrawler():
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'btn_area'))
  )
  removeElement()
  faceNorth()

def moveUpCamera():
  script = """
    btn_top = document.getElementsByClassName('btn_top');
    btn_top[0].click();
  """
  driver.execute_script(script)
  
def moveLeftCamera():
  script = """
    btn_left = document.getElementsByClassName('btn_left');
    btn_left[0].click();
  """
  driver.execute_script(script)
  
def goFront():
  # 모니터 전체 사이즈
  # 제 컴퓨터에 최적화되어있습니다!!
  width, height = pyautogui.size()
  print('monitor: {0}, {1}'.format(width, height))
  mouseX, mouseY = pyautogui.position()
  print('mouse: {0}, {1}'.format(mouseX, mouseY))
  pyautogui.moveTo(width / 2, height * 0.8)


# log = open('url_log_data.txt', 'w')
# log.writelines(driver.current_url)
def main():
  URL = 'https://map.naver.com/v5/?c=14369591.6228867,4195399.1714885,16,0,0,0,dha&p=W24Pz_oAOSKO7Jjias5jaA,-74.82,-5.43,80,Float'

  driver = webdriver.Chrome(executable_path='chromedriver')
  driver.maximize_window()
  driver.get(url=URL)

  delay = 3 # seconds

  initCrawler()

  try:
    # driver.save_screenshot("screenshot.png")
    # print("Capture!")
    goFront()
  except TimeoutException:
    print("Loading took too much time!")
