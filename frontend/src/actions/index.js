import { SET_INITIALIZED, SET_USER } from '../constants/action-types';

export const setInitialized = () => ({ type: SET_INITIALIZED });
export const setUser = (user) => ({ type: SET_USER, user: user });
