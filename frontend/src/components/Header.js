import React from 'react';
import ReactDOM from 'react-dom';

class Header extends React.Component {
  render() {
    return (
      <div class="navbar navbar-expand-lg navbar-dark bg-dark text-white mb-3">
        <a class="navbar-brand" href="#">
          Your header
        </a>
        <p class="navbar-nav ml-auto">Привет Маргарита!</p>
      </div>
    );
  }
}

export default Header;
