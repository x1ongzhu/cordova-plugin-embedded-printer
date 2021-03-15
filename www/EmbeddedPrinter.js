var exec = require('cordova/exec');

exports.printPic = function(payInfo, success, error) {
    exec(success, error, "EmbeddedPrinter", "printPic", [payInfo]);
};
