const path = require('path');
const webpack = require('webpack');

module.exports =
[{
  entry: './src/bundle/index.js',
  mode: 'development',
  output: {
    path: path.resolve(__dirname, '../main/resources/static/generated/js'),
    filename: 'bundle.js'
  }
}];
