// authReducer.js
//import {login, logout} from "./authActions";

const initialState = {
  isLoggedIn: false,
  username: "",
  password:"",
};

const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case "LOGIN":
      return {...state, isLoggedIn: true, username: action.payload.username, password: action.payload.password};
    case "LOGOUT":
      return {...state, isLoggedIn: false, username: "", password: ""};
    default:
      return state;
  }
};

export default authReducer;
