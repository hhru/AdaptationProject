import { createStore } from 'redux';
import rootReducer from '../reducers/index';

const store =
  module.hot && module.hot.data && module.hot.data.store
    ? module.hot.data.store
    : createStore(rootReducer);

export default store;
