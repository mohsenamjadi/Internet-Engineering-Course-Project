import React from "react";
import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Courses from "./pages/Courses/Courses";
import Home from "./pages/Home/Home";
import Login from "./pages/Login/Login";
import SignUp from "./pages/SignUp/SignUp";
import WeeklyPlan from "./pages/WeeklySchedule/WeeklyPlan";

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/courses">
            <Courses/>
        </Route>
        <Route path="/home">
            <Home/>
        </Route>
        <Route path="/login">
            <Login/>
        </Route>
        <Route path="/signup">
            <SignUp/>
        </Route>
        <Route path="/plan">
            <WeeklyPlan/>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
