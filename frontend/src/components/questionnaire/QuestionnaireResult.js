import React from 'react';
import axios from 'axios';
import { CustomAlert, Answer, QuestionData, Question, QuestionnaireParent } from './Common';

class QuestionnaireResult extends React.Component {
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
      disabled: true,
    };

    this.toggleAlert = this.toggleAlert.bind(this);
    this.prepareState = this.prepareState.bind(this);
    this.repairState = this.repairState.bind(this);
  }

  onRadioChange(answerId, questionId) {}

  onCommentChange(questionId, answerObj) {}

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

    const url = '/api/employee/' + this.props.match.params.id + '/questionnaire';
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
        //console.log(error);
        self.toggleAlert({
          status: true,
          message: 'Не удалось установить связь с сервером',
          color: 'danger',
        });
      });

    this.forceUpdate();
  }

  render() {
    return <QuestionnaireParent parent={this} />;
  }
}

export default QuestionnaireResult;
