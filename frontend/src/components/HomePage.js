import React from 'react';
import { connect } from 'react-redux';
import { Container, Row, Col } from 'reactstrap';

const mapStateToProps = (state) => {
  return { loggedIn: state.loggedIn };
};

class HomePage extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Row>
        <Col>
          <h1>Добро пожаловать!</h1>
        </Col>
      </Row>
    );
  }
}

export default connect(mapStateToProps)(HomePage);
