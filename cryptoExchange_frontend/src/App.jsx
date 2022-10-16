import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";
import "./App.css";

import AuthService from "./services/Auth.service";

import Login from "./components/authentication/Login";
import Register from "./components/authentication/Register";
import Home from "./components/Home";
import Profile from "./components/authentication/Profile";
import BoardPremium from "./components/boards/BoardPremium";
import BoardAdmin from "./components/boards/BoardAdmin";
import Shopping from "./components/Shopping";
import Exchange from "./components/Exchanges";
import AlienCoin from "./images/AlienCoin.png"

import EventBus from "./common/EventBus";

const App = () => {
  const [showPremiumBoard, setShowPremiumBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setShowPremiumBoard(user.roles.includes("ROLE_PREMIUM"));
      setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setShowPremiumBoard(false);
    setShowAdminBoard(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <div className="navbar-brand" href="#">
          <img src={AlienCoin} width="30" height="30" className="d-inline-block align-center" alt=""/>
          <Link to={"/"} className="navbar-brand">
            AlienCoin
          </Link>
        </div>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              Home
            </Link>
          </li>

          {showPremiumBoard && (
            <li className="nav-item">
              <Link to={"/mod"} className="nav-link">
                Premium Board
              </Link>
            </li>
          )}

          {showAdminBoard && (
            <li className="nav-item">
              <Link to={"/admin"} className="nav-link">
                Admin Board
              </Link>
            </li>
          )}
        </div>

        {currentUser && (
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/buy"} className="nav-link">
                Comprar
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/exchange"} className="nav-link">
                Transações
              </Link>
            </li>
          </div>
        )}

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {currentUser.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )}
      </nav>

      <div className="container mt-3">
        <Routes>
          <Route exact path={"/"} element={<Home />} />
          <Route exact path={"/home"} element={<Home />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/profile" element={<Profile />} />
          <Route path="/premium" element={<BoardPremium />} />
          <Route path="/admin" element={<BoardAdmin />} />
          <Route exact path="/buy" element={<Shopping />} />
          <Route exact path="/exchange" element={<Exchange />} />
        </Routes>
      </div>

    </div>
  );
};

export default App;