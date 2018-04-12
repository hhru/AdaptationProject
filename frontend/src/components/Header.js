import React from 'react';
import ReactDOM from 'react-dom';
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
} from 'reactstrap';

class Header extends React.Component {
  render() {
    return (
      <div>
        <Navbar color="dark" light expand="md" className="mb-3">
          <NavbarBrand className="ml-4 text-white" href="/">
            Adaptation
          </NavbarBrand>
          <Nav className="ml-auto" navbar>
            <NavItem className="mr-3">
              <NavLink className="text-white" href="/components/">
                Привет Маргарита!
              </NavLink>
            </NavItem>
          </Nav>
        </Navbar>
      </div>
    );
  }
}

export default Header;
