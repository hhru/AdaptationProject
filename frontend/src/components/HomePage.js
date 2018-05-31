import React from 'react';
import { connect } from 'react-redux';
import axios from 'axios';
import { Container, Row, Col, Button, Form } from 'reactstrap';

const mapStateToProps = (state) => {
  return { loggedIn: state.loggedIn };
};

class HomePage extends React.Component {
  constructor(props) {
    super(props);

    this.onBuy = this.onBuy.bind(this);
  }

  onBuy(e) {
    e.preventDefault();
    this.props.history.push('/donate');
  }

  render() {
    return (
      <div>
        <Row>
          <Col sm={{ size: 10, offset: 1 }}>
            <div className="empty-for-main-page-left" />
            <h1>Познакомьтесь с Adaptation - вашим умным помощником</h1>
            <p>
              Мы заботимся о ваших HR-процессах, пока вы заботитесь о своих сотрудниках. Любая
              информация по прохождению испытательного срока будет всегда под рукой,
              синхронизирована и доступна для работы.
            </p>
          </Col>
        </Row>

        <Row>
          <Col sm={{ size: 2, offset: 6 }}>
            <div className="empty-for-main-page-right" />
            {!this.props.loggedIn && (
              <Form action="/api/login" method="POST">
                <Button color="primary" type="submit">
                  Попробовать бесплатно
                </Button>
              </Form>
            )}
          </Col>
          <Col sm={{ size: 3 }} className="ml-5">
            <div className="empty-for-main-page-right" />
            <Button color="primary" outline onClick={this.onBuy}>
              Приобрести сейчас
            </Button>
          </Col>
        </Row>
      </div>
    );
  }
}

export default connect(mapStateToProps)(HomePage);
