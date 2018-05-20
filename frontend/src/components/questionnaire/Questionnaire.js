import React from 'react';
import axios from 'axios';
import { Col, Button, Row } from 'reactstrap';
import { Answer, CustomAlert, QuestionData, Question, QuestionnaireParent } from './Common';

class Questionnaire extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      answers: [],
      questions: [],
      isComplete: false,
      isManager: false,
      alert: {
        status: false,
        message: '',
        color: '',
      },
      disabled: false,
    };

    this.submit = this.submit.bind(this);
    this.onCommentChange = this.onCommentChange.bind(this);
    this.toggleAlert = this.toggleAlert.bind(this);
    this.prepareState = this.prepareState.bind(this);
  }

  toggleAlert(alertObj) {
    this.setState({
      alert: alertObj,
    });
    setTimeout(() => {
      this.setState({
        alert: {
          status: false,
        },
      });
    }, 2200);
  }

  validate(isManager) {
    const n = isManager ? 19 : 17;
    for (let i = 0; i < n; i++) {
      let num = null;
      let txt = null;
      if (![5, 14, 16].includes(i)) {
        if (this.state.answers[i] == undefined) {
          return false;
        }

        if (this.state.answers[i].answerNumber == null) {
          return false;
        }

        txt = this.state.answers[i].answerText;
        num = this.state.answers[i].answerNumber;
      }

      if (
        (([1, 3, 6, 18].includes(i) && num == 3) ||
          (i == 12 && num > 1) ||
          (i == 13 && num == 5) ||
          (i == 15 && num > 1)) &&
        (txt == null || txt.length == 0)
      ) {
        return false;
      }
      if (
        ([1, 3, 6, 18].includes(i) && num < 3) ||
        (i == 12 && num < 2) ||
        (i == 13 && num < 5) ||
        (i == 15 && num < 2)
      ) {
        this.state.answers[i].answerText = null;
      }
    }

    return true;
  }

  submit() {
    if (!this.validate(this.state.isManager)) {
      this.toggleAlert({
        status: true,
        message: 'Не все поля заполнены',
        color: 'danger',
      });
      return;
    }

    const url = '/api/questionnaire/submit';
    const self = this;

    axios
      .post(url, {
        answers: self.state.answers,
        isComplete: true,
        isManager: self.state.isManager,
        key: self.props.match.params.id,
      })
      .then(function(response) {
        self.toggleAlert({
          status: true,
          message: 'Результаты отправлены',
          color: 'success',
        });
        self.state.isComplete = true;
        self.forceUpdate();
      })
      .catch(function(error) {
        self.toggleAlert({
          status: true,
          message: 'Не удалось установить связь с сервером',
          color: 'danger',
        });
        //console.log(error);
      });
  }

  onRadioChange(answerId, questionId) {
    if (this.state.isComplete) {
      return;
    }
    this.state.answers[questionId].questNumber = questionId;
    this.state.answers[questionId].answerNumber = answerId;

    this.forceUpdate();
  }

  onCommentChange(questionId, answerObj) {
    if (this.state.isComplete) {
      return;
    }
    this.state.answers[questionId] = answerObj;
    this.forceUpdate();
  }

  repairState() {
    const self = this;

    this.state.questions.forEach(function(element) {
      if (self.state.answers[element.id] == undefined) {
        self.state.answers[element.id] = {
          questNumber: element.id,
          answerNumber: null,
          answerText: null,
        };
      }
    });
  }

  prepareState() {
    const self = this;
    //this.setState(QuestionData.prototype.getQuestions());
    this.state.questions = QuestionData.prototype.getQuestions().questions;

    this.state.questions.forEach(function(element) {
      self.state.answers[element.id] = {
        questNumber: element.id,
        answerNumber: null,
        answerText: null,
      };
    });
  }

  componentDidMount() {
    this.prepareState();

    const url = '/api/questionnaire/' + this.props.match.params.id;
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        self.state.answers = response.data.answers;
        self.state.isComplete = response.data.isComplete;
        self.state.isManager = response.data.isManager;
        self.repairState();
        self.forceUpdate();
      })
      .catch(function(error) {
        console.log(error);
        this.toggleAlert({
          status: true,
          message: 'Не удалось установить связь с сервером',
          color: 'danger',
        });
      });

    this.forceUpdate();
  }

  render() {
    return (
      <div className="mt-4">
        <QuestionnaireParent parent={this} />

        <Row className="mb-5">
          <Col className="mb-5">
            {!this.state.isComplete && (
              <Button className="float-right mb-5" outline color="secondary" onClick={this.submit}>
                Завершить
              </Button>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

export default Questionnaire;
