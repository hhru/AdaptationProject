import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaCircle from 'react-icons/lib/fa/circle';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';

import { Jumbotron, Alert } from 'reactstrap';
import { Container, Row, Col } from 'reactstrap';
import { ListGroup, ListGroupItem } from 'reactstrap';
import { Form, FormGroup, Input } from 'reactstrap';
import { Popover, PopoverHeader, PopoverBody } from 'reactstrap';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import '!style-loader!css-loader!./app.css';

class EmployeePage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      modal: false,
      alert: false,
      commentValue: '',
      employeeId: this.props.match.params.id,
      data: {
        id: null,
        currentWorkflowStep: '',
        employee: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        chief: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        mentor: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        hr: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        employmentDate: null,
        workflow: [
          {
            id: null,
            type: '',
            status: '',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
        ],
        comments: [],
      },
    };

    this.toggleModal = this.toggleModal.bind(this);
    this.toggleAlert = this.toggleAlert.bind(this);
    this.onCommentChange = this.onCommentChange.bind(this);
    this.commentBoxSubmit = this.commentBoxSubmit.bind(this);
    this.commentRemove = this.commentRemove.bind(this);

    var nextStep = new NextStep();
  }

  onCommentChange(e) {
    this.setState({
      commentValue: e.target.value,
    });
  }

  componentDidMount() {
    const url = '/api/employee/' + this.state.employeeId;
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        console.log(response.data);
        self.setState({
          data: response.data,
        });
      })
      .catch(function(error) {
        self.toggleAlert();
        console.log(error);
      });
  }

  nextStep(self) {
    const url = '/api/employee/' + self.state.employeeId + '/step/next';
    axios
      .put(url)
      .then(function(response) {
        for (var i = 0; i < self.state.data.workflow.length; i++) {
          if (self.state.data.workflow[i].status == 'CURRENT') {
            self.state.data.workflow[i].status = 'DONE';
            self.state.data.workflow[i].overdue = false;
            if (i < self.state.data.workflow.length - 1) {
              self.state.data.workflow[i + 1].status = 'CURRENT';
            }
            break;
          }
        }
        self.forceUpdate();
        self.toggleAlert();
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  timeLeft(emplDataString) {
    if (emplDataString == null) return;
    var result = 'До конца испытательного срока: ';
    var emplYear = parseInt(emplDataString.split('-')[0]);
    var emplMonth = parseInt(emplDataString.split('-')[1]);
    var emplDay = parseInt(emplDataString.split('-')[2]);

    var emplData = new Date(emplYear, emplMonth + 2, emplDay);
    var delta = emplData - new Date();
    var yearLeft = parseInt(delta / 1000 / 60 / 60 / 24 / 30.4 / 12);
    var monthLeft = parseInt(
      delta / 1000 / 60 / 60 / 24 / 30.4 - yearLeft * 12
    );
    var dayLeft = parseInt(
      delta / 1000 / 60 / 60 / 24 - monthLeft * 30.4 - yearLeft * 30.4 * 12
    );

    if (yearLeft > 0) {
      result += yearLeft + ' лет ';
    }
    if (monthLeft > 0) {
      result += monthLeft + ' мес. ';
    }
    if (dayLeft > 0) {
      result += dayLeft + ' дн.';
    }
    return result;
  }

  toggleModal() {
    this.setState({
      modal: !this.state.modal,
    });
  }

  toggleAlert() {
    this.setState({
      alert: true,
    });
    setTimeout(() => {
      this.setState({
        alert: false,
      });
    }, 2200);
  }

  commentBoxSubmit(e) {
    e.preventDefault();
    if (this.state.commentValue == '') return;
    var newId = 0;
    if (this.state.data.comments.length > 0)
      newId =
        Math.max.apply(
          Math,
          this.state.data.comments.map(function(x) {
            return x.id;
          })
        ) + 1;
    this.state.data.comments.push({
      id: newId,
      name: 'Вы',
      text: this.state.commentValue,
      tag: 'tag2',
    });
    this.setState({
      commentValue: '',
    });

    //privet. kak tebe moi kostyl?
    setTimeout(() => {
      document.getElementById('commentBox').scrollTop += 1200;
    }, 0);
  }

  commentRemove(commentId) {
    this.state.data.comments = this.state.data.comments.filter(
      (x) => x.id != commentId
    );
    this.forceUpdate();
  }

  render() {
    const {
      firstName: employeeFirstName,
      middleName: employeeMiddleName,
      lastName: employeeLastName,
      email: employeeEmail,
    } = this.state.data.employee;
    const {
      firstName: chiefFirstName,
      middleName: chiefMiddleName,
      lastName: chiefLastName,
    } = this.state.data.chief;
    const {
      firstName: mentorFirstName,
      middleName: mentorMiddleName,
      lastName: mentorLastName,
    } = this.state.data.mentor;
    const {
      firstName: hrFirstName,
      middleName: hrMiddleName,
      lastName: hrLastName,
    } = this.state.data.hr;
    const employmentDate = this.state.data.employmentDate;
    const workflow = this.state.data.workflow;
    const timeLeft = this.timeLeft(this.state.data.employmentDate);

    return (
      <Container>
        <Jumbotron>
          <Row className="mb-4">
            <Col sm={{ size: 5, offset: 1 }}>
              <h3 className="mb-0 font-weight-bold">
                {`${employeeFirstName} ${employeeMiddleName} ${employeeLastName}`}
              </h3>
              <div className="mb-1 ml-2 text-info"> {employeeEmail} </div>
            </Col>
          </Row>

          <Row className="mb-2">
            <Col sm={{ size: 5, offset: 1 }}>
              <div className="ml-4">
                <p className="mb-2 text-muted">
                  {`Начальник: ${chiefFirstName} ${
                    chiefMiddleName == null ? '' : chiefMiddleName
                  } ${chiefLastName}`}
                </p>
                <p className="mb-2 text-muted">
                  {`Ментор: ${mentorFirstName} ${
                    mentorMiddleName == null ? '' : chiefMiddleName
                  } ${mentorLastName}`}
                </p>
                <p className="text-muted">
                  {`HR: ${hrFirstName} ${
                    hrMiddleName == null ? '' : hrMiddleName
                  } ${hrLastName}`}
                </p>
              </div>
            </Col>
            <Col sm={{ size: 5 }} className="mt-0 ml-5">
              <div className="">
                <p className="font-italic mb-2">
                  {`Дата выхода на работу: ${employmentDate}`}
                </p>
                <p className="font-italic"> {timeLeft} </p>
              </div>
            </Col>
          </Row>

          <Row>
            <Col sm={{ size: 5, offset: 1 }} className="mt-4">
              <Workflow data={workflow} />
              <NextStep data={this} />
            </Col>
            <Col sm={{ size: 5 }}>
              <div className="ml-2">
                <h4>
                  <span className="text-muted">Комментарии</span>
                </h4>
              </div>
              <div>
                {this.state.data.comments != null && (
                  <Comments
                    data={this.state.data.comments}
                    func={this.commentRemove}
                  />
                )}
                <Form onSubmit={(e) => this.commentBoxSubmit(e)}>
                  <FormGroup>
                    <Input
                      rows="1"
                      type="text"
                      name="text"
                      placeholder="Написать комментарий"
                      onChange={this.onCommentChange}
                      value={this.state.commentValue}
                    />
                  </FormGroup>
                </Form>
              </div>
            </Col>
          </Row>

          <Row>
            <Col sm={{ size: 12, offset: 0 }} className="mt-0">
              <div>
                <Alert
                  color="danger"
                  isOpen={this.state.alert}
                  toggle={this.toggleAlert}
                >
                  Не удалось установить связь с сервером
                </Alert>
              </div>
            </Col>
          </Row>
        </Jumbotron>
      </Container>
    );
  }
}

class NextStep extends React.Component {
  constructor(props) {
    super(props);

    this.click = this.click.bind(this);
  }

  click() {
    var parent = this.props.data;

    parent.nextStep(parent);
    parent.toggleModal();
  }

  render() {
    var parent = this.props.data;
    return (
      <div>
        <Button
          outline
          color="secondary"
          className="mt-5"
          onClick={parent.toggleModal}
        >
          Перевести далее
        </Button>
        <Modal
          isOpen={parent.state.modal}
          toggle={parent.toggleModal}
          className={parent.className}
        >
          <ModalHeader toggle={parent.toggleModal}>Подтверждение</ModalHeader>
          <ModalBody>
            Отменить перевод на следующий этап можно будет в меню
            редактирования. Наверное, на некоторых этапах в этом окошке можно
            будет добавлять некторую информацию. А может на каждом? например,
            комментарии к этапу писать здесь. Ну и конечно же нужно ли вообще
            это окошко?
          </ModalBody>
          <ModalFooter>
            <Button outline color="secondary" onClick={this.click}>
              Перевести
            </Button>
            <Button outline color="danger" onClick={parent.toggleModal}>
              Отмена
            </Button>
          </ModalFooter>
        </Modal>
      </div>
    );
    /*
    return (
      <Button outline color="secondary" className="mt-5" onClick={() => parent.nextStep(parent)}>
        Перевести далее
      </Button>
    );*/
  }
}

class Workflow extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        {this.props.data.map((workflowStageData) => (
          <WorkflowStage
            deadlineDate={workflowStageData.deadlineDate}
            status={workflowStageData.status}
            overdue={workflowStageData.overdue}
            type={workflowStageData.type}
            key={workflowStageData.id}
            id={workflowStageData.id}
          />
        ))}
      </div>
    );
  }
}

class WorkflowStage extends React.Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      popoverOpen: false,
    };
    this.selectIcon = this.selectIcon.bind(this);
  }

  selectIcon(status, overdue) {
    switch (overdue) {
      case true:
        return <FaExclamationCircle size={50} color="#DF6B62" />;
      default:
        switch (status) {
          case 'DONE':
            return <FaCheckCircle size={50} color="#70BD71" />;
          case 'CURRENT':
            return <FaAdjust size={50} color="#BDB370" />;
          default:
            return <FaCircle size={50} color="#C2C2C2" />;
        }
    }
  }

  typeTranslate(type) {
    switch (type) {
      case 'ADD':
        return 'Подготовка к сопровождению';
      case 'TASK_LIST':
        return 'Задачи на испытательный срок';
      case 'WELCOME_MEETING':
        return 'Велком встреча';
      case 'INTERIM_MEETING':
        return 'Промежуточная встреча';
      case 'INTERIM_MEETING_RESULT':
        return 'Результаты промежуточной встречи';
      case 'FINAL_MEETING':
        return 'Итоговая встреча';
      case 'FINAL_MEETING_RESULT':
        return 'Результаты итоговой встречи';
      case 'QUESTIONNAIRE':
        return 'Опросник';
    }
  }

  getDescription(type) {
    switch (type) {
      case 'ADD':
        return 'Ожидается день выхода на работу. Сейчас начальник готовит задачи на испытательный срок.';
      case 'TASK_LIST':
        return 'Необходимо получить задачи от руководителя, распечатать и подписать их.';
      case 'WELCOME_MEETING':
        return 'Необходимо забронировать перговорную конату и провести welcome встречу.';
      case 'INTERIM_MEETING':
        return 'Необходимо забронировать перговорную конату и провести промежуточную встречу.';
      case 'INTERIM_MEETING_RESULT':
        return 'Необходимо заполнить результаты промежуточной встречи.';
      case 'FINAL_MEETING':
        return 'Необходимо забронировать перговорную конату и провести итоговую встречу.';
      case 'FINAL_MEETING_RESULT':
        return 'Необходимо заполнить результаты итоговой встречи.';
      case 'QUESTIONNAIRE':
        return 'Сотрудник получил опросник для новчика и работает над его заполнением.';
    }
  }

  toggle() {
    this.setState({
      popoverOpen: !this.state.popoverOpen,
    });
  }

  render() {
    const { deadlineDate, status, overdue, type } = this.props;

    return (
      <div
        id={'Popover-' + this.props.id}
        className="workflow-leonid"
        onClick={this.toggle}
      >
        {deadlineDate}
        {this.selectIcon(status, overdue)}
        <span className="ml-3">{this.typeTranslate(type)}</span>
        <br />
        <Popover
          placement="bottom"
          isOpen={this.state.popoverOpen}
          target={'Popover-' + this.props.id}
          toggle={this.toggle}
        >
          <PopoverHeader>{this.typeTranslate(type)}</PopoverHeader>
          <PopoverBody>{this.getDescription(type)}</PopoverBody>
        </Popover>
      </div>
    );
  }
}

class Comments extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <ListGroup id="commentBox" className="mb-0 anyClass">
        {this.props.data.map((commentsData) => (
          <CommentsData
            tag={commentsData.tag}
            text={commentsData.text}
            name={commentsData.name}
            key={commentsData.id}
            id={commentsData.id}
            func={this.props.func}
          />
        ))}
      </ListGroup>
    );
  }
}

class CommentsData extends React.Component {
  constructor(props) {
    super(props);

    this.mouseIn = this.mouseIn.bind(this);
    this.mouseOut = this.mouseOut.bind(this);
    this.state = {
      isHovering: false,
    };
  }

  mouseIn() {
    this.setState({
      isHovering: true,
    });
  }

  mouseOut() {
    this.setState({
      isHovering: false,
    });
  }

  nameWithDots(name) {
    return name + ':';
  }

  render() {
    const { tag, text, name, id } = this.props;

    return (
      <ListGroupItem
        className=""
        onMouseEnter={this.mouseIn}
        onMouseLeave={this.mouseOut}
      >
        <Row>
          <Col sm={{ size: 8, offset: 0 }}>
            <h6 className="my-0 mb-2">{this.nameWithDots(name)}</h6>
          </Col>
          <Col>
            {this.state.isHovering && (
              <small
                className="text-muted comment-delete"
                onClick={() => this.props.func(id)}
              >
                ✖
              </small>
            )}
          </Col>
        </Row>
        <Row>
          <span className="ml-2">{text}</span>
        </Row>
      </ListGroupItem>
    );
  }
}

export default EmployeePage;
