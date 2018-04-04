const webpack = require('webpack');
const merge = require('webpack-merge');
const common = require('./webpack.config.common.js');

const port = process.env.PORT || 3000;
const backendHost = process.env.BACKEND_HOST || 'localhost';
const backendPort = process.env.BACKEND_PORT || 9999;

module.exports = merge(common, {
  mode: 'development',
  entry: ['react-hot-loader/patch', './src/index.js'],
  output: {
    filename: 'bundle.[hash].js',
    publicPath: '/',
  },
  devtool: 'eval-source-map',
  module: {
    rules: [
      {
        test: /\.css$/,
        use: [
          {
            loader: 'style-loader',
          },
          {
            loader: 'css-loader',
            options: {
              modules: false,
              camelCase: true,
              sourceMap: true,
            },
          },
          {
            loader: 'postcss-loader',
            options: {
              config: {
                ctx: {
                  autoprefixer: {
                    browsers: 'last 2 versions',
                  },
                },
              },
            },
          },
        ],
      },
    ],
  },
  plugins: [new webpack.HotModuleReplacementPlugin()],
  devServer: {
    host: 'localhost',
    port: port,
    historyApiFallback: true,
    open: true,
    hot: true,
    proxy: {
      '/api/': {
        target: `http://${backendHost}:${backendPort}`,
        pathRewrite: { '^/api': '' },
      },
    },
  },
});
