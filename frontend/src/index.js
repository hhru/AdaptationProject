import { AppContainer } from 'react-hot-loader';
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import store from './store/index';
import App from './components/App';

const render = (Component) =>
  ReactDOM.render(
    <Provider store={store}>
      <AppContainer>
        <Router>
          <Route path="/" component={Component} />
        </Router>
      </AppContainer>
    </Provider>,
    document.getElementById('root')
  );

render(App);

// Webpack Hot Module Replacement API
if (module.hot) {
  module.hot.accept('./components/App', () => render(App));
  module.hot.dispose((data) => {
    data.store = store;
  });
}
