import React from 'react';
import ReactDOM from 'react-dom';
import { Switch, BrowserRouter as Router, Route } from 'react-router-dom';

import AddEmployee from './AddEmployee';
import ListEmployees from './ListEmployees';
import HomePage from './HomePage';
import NotFound from './NotFound';

import { p } from './App.css';


const App = () => {
  return (
   <Router>
     <div>
       <Switch>
         <Route exact path="/" component={HomePage} />
         <Route exact path="/add_employee" component={AddEmployee} />
         <Route exact path="/list_employees" component={ListEmployees} />
         <Route component={NotFound} />
       </Switch>
     </div>
   </Router>
  );
};


export default App;

