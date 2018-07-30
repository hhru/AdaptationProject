import { SET_INITIALIZED, SET_USER } from '../constants/action-types';

const initialState = {
  initialized: false,
  user: null,
};

const rootReducer = (state = initialState, action) => {
  switch (action.type) {
    case SET_INITIALIZED:
      return { ...state, initialized: true };
    case SET_USER:
      return { ...state, user: action.user };
    default:
      return state;
  }
};

export default rootReducer;
