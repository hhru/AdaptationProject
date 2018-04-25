import { SET_INITIALIZED, SET_LOGGED_IN } from '../constants/action-types';

const initialState = {
  initialized: false,
  loggedIn: false,
};

const rootReducer = (state = initialState, action) => {
  switch (action.type) {
    case SET_INITIALIZED:
      return { ...state, initialized: true };
    case SET_LOGGED_IN:
      return { ...state, loggedIn: true };
    default:
      return state;
  }
};

export default rootReducer;
