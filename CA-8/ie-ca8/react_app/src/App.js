import React from "react";
import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Courses from "./pages/Courses/Courses";
import Home from "./pages/Home/Home";
import Login from "./pages/Login/Login";
import SignUp from "./pages/SignUp/SignUp";
import WeeklyPlan from "./pages/WeeklySchedule/WeeklyPlan";
import AuthService from "./services/AuthService";
import ForgetPassword from "./pages/ForgetPassword/ForgetPassword";
import ResetPassword from "./pages/ForgetPassword/ResetPassword";
import { QueryParamProvider } from 'use-query-params';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/login" component={()=>AuthService.getUserToken()?<Home/>:<Login/>} />
        <Route exact path="/signup" component={()=>AuthService.getUserToken()?<Home/>:<SignUp/>} />
        <Route exact path="/" component={()=>AuthService.getUserToken()?<Home/>:<Login/>} />
        <Route exact path="/courses" component={()=>AuthService.getUserToken()?<Courses/>:<Login/>} />
        <Route exact path="/plan" component={()=>AuthService.getUserToken()?<WeeklyPlan/>:<Login/>} />
        <Route exact path="/forgetpassword" component={()=>AuthService.getUserToken()?<Home/>:<ForgetPassword/>} />
        <QueryParamProvider>
          <Route exact path="/resetpassword" component={()=>AuthService.getUserToken()?<Home/>:<ResetPassword/>} />
        </QueryParamProvider>
      </Switch>
    </Router>
  );
}

export default App;
