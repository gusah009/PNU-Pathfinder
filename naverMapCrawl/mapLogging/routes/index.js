var express = require('express');

var router = express.Router();

router.get('/', async (req, res, next) => {
  try {
    res.render('index');
  } catch (err) {
    console.error(err);
    next(err);
  }
});


module.exports = router;