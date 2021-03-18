import React from 'react';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { Header } from "./Header/Header";
import { Main } from "./Main/Main";
import './App.css';


export function App() {
  
  return (
    <Router>
      
      <Main />
    </Router>
  );
}