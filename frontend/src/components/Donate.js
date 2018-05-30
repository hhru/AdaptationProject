import React from 'react';
import { connect } from 'react-redux';
import axios from 'axios';
import { Container, Row, Col, Button, Form } from 'reactstrap';

const mapStateToProps = (state) => {
  return { loggedIn: state.loggedIn };
};

class Donate extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Row>
        <Col>
          <h1>Coming soon</h1>
        </Col>
      </Row>
    );
  }
}

export default Donate;
