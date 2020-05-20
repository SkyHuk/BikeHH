const path = require('path');

module.exports = 
[{
  entry: './src/bundle/index.js',
  mode: 'development',
  output: {
    path: path.resolve(__dirname, '../main/resources/static/generated/js'),
    filename: 'bundle.js'
  }
}];
