// authActions.js
// export const login = () => ({ type: 'LOGIN' });
// export const logout = () => ({ type: 'LOGOUT' });

//const LOGIN = 'LOGIN';
//const LOGOUT = 'LOGOUNT';

// authActions.js
export const login = (username, password) => {
  return {
    type: 'LOGIN',
    payload: {
      username, password,
    }
  };
};


export const logout = () => {
  return {
    type: 'LOGOUT'
  };
};