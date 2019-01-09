import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJwtTokenInHeaders from "../securityUtils/setJwtTokenInHeaders";
import jwt_decode from "jwt-decode";

export const login = (loginRequest, history) => async dispatch => {
  try {
    const res = await axios.post("/authentication/login", loginRequest);
    const { token } = res.data;
    localStorage.setItem("jwtToken", token);
    setJwtTokenInHeaders(token);
    const decodedToken = jwt_decode(token);
    history.push("/dashboard");
    dispatch({
      type: SET_CURRENT_USER,
      payload: decodedToken
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem("jwtToken");
  setJwtTokenInHeaders(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {}
  });
};
