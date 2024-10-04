import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import '../styles/Login.css';

import { FaUser } from "react-icons/fa";
import { TbEyeClosed, TbEyeUp } from "react-icons/tb";
import logo from '../assets/logo.png';
import { jwtDecode } from 'jwt-decode';

const Login = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage('');

    const userData = {
      username,
      password
    };

    try {
      const response = await axios.post('http://localhost:8080/user/login', userData);
      if (response.status === 200) {
        const token = response.headers['jwt-token']; // Assuming token is in headers
        if (token) {
          const tokenDecoded = jwtDecode(token);
          const authorities = tokenDecoded.authorities;

          // Store token and role in localStorage
          localStorage.setItem('token', token);
          localStorage.setItem('exp', tokenDecoded.exp);
          localStorage.setItem('role', authorities[0]);  // Store the user role

          if (authorities.includes('ROLE_ROLE_ADMIN')) {
            navigate('/account/admin');
          } else {
            setErrorMessage('Unauthorized access.');
          }
        }
      } else {
        alert('Login failed. Please check your credentials and try again.');
      }
    } catch (error) {
      console.error('Error:', error.message);
      if (error.response && error.response.status === 401) {
        setErrorMessage('Invalid username or password.');
      } else {
        setErrorMessage('An error occurred while processing your request.');
      }
    }
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className="semi-body">
      <div className="form-box-login">
        <form onSubmit={handleLogin} className="form-container-login">
          <div className="header">
            <div className="logo">
              <img src={logo} alt="Logo" id="logo" />
            </div>
            <h1>Login</h1>
          </div>

          <div className="field-box">
            <label>Username</label>
            <div className="insert">
              <input type="text" required onChange={(e) => setUsername(e.target.value)} />
              <FaUser className="icon" />
            </div>
          </div>

          <div className="field-box field-box-password">
            <label>Password</label>
            <div className="insert">
              <input type={showPassword ? "text" : "password"} required onChange={(e) => setPassword(e.target.value)} />
              {showPassword ? (
                <TbEyeUp className="icon" onClick={togglePasswordVisibility} />
              ) : (
                <TbEyeClosed className="icon" onClick={togglePasswordVisibility} />
              )}
            </div>
          </div>

          {errorMessage && <p className="error-message">{errorMessage}</p>}

          <button type="submit" className="login">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;
