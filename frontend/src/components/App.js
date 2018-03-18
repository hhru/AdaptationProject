import React from 'react';
import ReactDOM from 'react-dom';
import { Switch, BrowserRouter as Router, Route } from 'react-router-dom';

import AddEmployee from './AddEmployee';
import ListEmployees from './ListEmployees';
import Home from './Home';
import NotFound from './NotFound';
import LogedInHome from './LogedInHome';

import { p } from './App.css';


const App = () => {
  return (
   <Router>
     <div>
       <Switch>
         <Route exact path="/" component={Home} />
         <Route exact path="/home" component={LogedInHome} />
         <Route exact path="/add_employee" component={AddEmployee} />
         <Route exact path="/list_employees" component={ListEmployees} />
         <Route component={NotFound} />
       </Switch>
     </div>
   </Router>
  );
};


export default App;

