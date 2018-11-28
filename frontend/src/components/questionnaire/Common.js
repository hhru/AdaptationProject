import React from 'react';
import { FormGroup, Label, Input, Alert, Form, Col, Row } from 'reactstrap';

export class QuestionnaireParent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let parent = this.props.parent;
    return (
      <div>
        <Row>
          <Col>
            <h5>
              Дорогой коллега, мы надеемся, что первые три месяца твоей работы прошли продуктивно.
              Мы просим тебя максимально честно и объективно оценить работу коллег, которые имели
              отношение к тому, чтобы ты как можно быстрее адаптировался в компании.
            </h5>
            <br />
            {parent.state.questions.map(
              (question) =>
                (parent.state.isManager || question.id < 17) && (
                  <Question
                    question={question}
                    chosen={parent.state.answers[question.id]}
                    key={question.id}
                    disabled={parent.state.disabled}
                    parent={parent}
                  />
                )
            )}
            <FormGroup>
              <Label for="exampleText">Ниже ты можешь написать свои пожелания и комментарии</Label>
              <Input
                type="textarea"
                name="text"
                id="exampleText"
                disabled={parent.state.disabled}
                onChange={(e) =>
                  parent.onCommentChange(19, {
                    questNumber: 19,
                    answerNumber: null,
                    answerText: e.target.value,
                  })
                }
              />
            </FormGroup>
          </Col>
        </Row>

        <Row>
          <Col>
            <div className="fixed-alert">
              <CustomAlert parent={parent} />
            </div>
          </Col>
        </Row>
      </div>
    );
  }
}

export class Question extends React.Component {
  constructor(props) {
    super(props);

    this.onRadioChange = this.onRadioChange.bind(this);
    this.onCommentChange = this.onCommentChange.bind(this);
  }

  idMapping(questNumber, currentAnswer) {
    switch (questNumber) {
      case 1:
      case 3:
      case 6:
      case 18:
        return 3;
      case 12:
      case 15:
        return currentAnswer < 2 ? 2 : currentAnswer;
      case 13:
        return 5;
      default:
        return null;
    }
  }

  onCommentChange(e) {
    const currentAnswer = this.props.chosen.answerNumber;
    const answerNumber = this.idMapping(this.props.question.id, currentAnswer);
    const answerText = e.target.value;

    this.props.parent.onCommentChange(this.props.question.id, {
      questNumber: this.props.question.id,
      answerNumber: answerNumber,
      answerText: answerText,
    });
  }

  onRadioChange(answerId) {
    this.props.parent.onRadioChange(answerId, this.props.question.id);
  }

  render() {
    const id = this.props.question.id;
    const quest = this.props.question.quest;
    const answers = this.props.question.answers;
    const chosen = this.props.chosen;

    return (
      <Form>
        <FormGroup tag="fieldset">
          <legend>
            {id + 1}. {quest}
          </legend>
          {answers.map((answer) => (
            <Answer answer={answer} chosen={chosen.answerNumber} key={answer.id} parent={this} />
          ))}
        </FormGroup>

        {[1, 3, 5, 6, 12, 13, 14, 15, 16, 18].includes(id) && (
          <FormGroup>
            {id == 12 && <Label for="exampleText">Какие вопросы остались без ответа</Label>}
            <Input
              disabled={this.props.disabled}
              type="textarea"
              name="text"
              id="exampleText"
              onChange={this.onCommentChange}
              value={chosen.answerText == null ? '' : chosen.answerText}
            />
          </FormGroup>
        )}
      </Form>
    );
  }
}

export class Answer extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const answer = this.props.answer;
    const checked = answer.id == this.props.chosen;

    return (
      <FormGroup check>
        <Label check>
          <Input
            type="radio"
            name="radio1"
            value={answer.id}
            onChange={(ansId) => this.props.parent.onRadioChange(ansId.target.value)}
            checked={checked}
          />
          {answer.text}
        </Label>
      </FormGroup>
    );
  }
}

export class CustomAlert extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let parent = this.props.parent;

    return (
      <Alert color={parent.state.alert.color} isOpen={parent.state.alert.status}>
        {parent.state.alert.message}
      </Alert>
    );
  }
}

export class QuestionData {
  getQuestions() {
    return {
      questions: [
        {
          id: 0,
          quest:
            'Была ли проведена с тобой welcome встреча с HR менеджером в первые дни работы в компании?',
          answers: [
            {
              id: 0,
              text: 'Да',
            },
            {
              id: 1,
              text: 'Нет',
            },
          ],
        },
        {
          id: 1,
          quest: 'Насколько полезна была для тебя изложенная информация на welcome встрече?',
          answers: [
            {
              id: 0,
              text: 'Полезна, помогла быстрее адаптироваться в компании',
            },
            {
              id: 1,
              text: 'Частично полезна, после встречи появились новые вопросы',
            },
            {
              id: 2,
              text: 'Бесполезна, ничего из рассказанного мне не пригодилось',
            },
            {
              id: 3,
              text: 'Другой вариант',
            },
          ],
        },
        {
          id: 2,
          quest: 'Была ли проведена с тобой обучающая встреча с IT отделом?',
          answers: [
            {
              id: 0,
              text: 'Да',
            },
            {
              id: 1,
              text: 'Нет',
            },
          ],
        },
        {
          id: 3,
          quest: 'Кто ознакомил тебя с задачами на испытательный срок?',
          answers: [
            {
              id: 0,
              text: 'Наставник',
            },
            {
              id: 1,
              text: 'Руководитель',
            },
            {
              id: 2,
              text: 'HR менеджер',
            },
            {
              id: 3,
              text: 'Другой вариант',
            },
          ],
        },
        {
          id: 4,
          quest: 'Когда тебя ознакомили с задачами на испытательный срок?',
          answers: [
            {
              id: 0,
              text: 'В первый день работы в компании',
            },
            {
              id: 1,
              text: 'В течении первой рабочей недели',
            },
            {
              id: 2,
              text: 'В течении первого месяца работы',
            },
            {
              id: 3,
              text: 'Я не видел своих задач на испытательный срок',
            },
          ],
        },
        {
          id: 5,
          quest: 'Кто был твоим наставником?',
          answers: [],
        },
        {
          id: 6,
          quest: 'Кто тебе сообщил о нем?',
          answers: [
            {
              id: 0,
              text: 'Руководитель',
            },
            {
              id: 1,
              text: 'HR менеджер',
            },
            {
              id: 2,
              text: 'Сам наставник',
            },
            {
              id: 3,
              text: 'Другой вариант',
            },
          ],
        },
        {
          id: 7,
          quest: 'На какой день работы в компании ты познакомился с наставником?',
          answers: [
            {
              id: 0,
              text: 'В первый рабочий день',
            },
            {
              id: 1,
              text: 'В течении первой рабочей недели',
            },
            {
              id: 2,
              text: 'В течении первого рабочего месяца',
            },
            {
              id: 3,
              text: 'Мы так и не познакомились',
            },
          ],
        },
        {
          id: 8,
          quest:
            'Как ты можешь охарактеризовать периодичность общения с наставником в первый месяц работы?',
          answers: [
            {
              id: 0,
              text: 'Каждый день',
            },
            {
              id: 1,
              text: '2 — 3 раза в неделю',
            },
            {
              id: 2,
              text: 'Один раз в неделю',
            },
            {
              id: 3,
              text: '2 — 3 раза в месяц',
            },
            {
              id: 4,
              text: 'Вообще не общались',
            },
          ],
        },
        {
          id: 9,
          quest:
            'Как ты можешь охарактеризовать периодичность общения с наставником во второй месяц работы?',
          answers: [
            {
              id: 0,
              text: 'Каждый день',
            },
            {
              id: 1,
              text: '2 — 3 раза в неделю',
            },
            {
              id: 2,
              text: 'Один раз в неделю',
            },
            {
              id: 3,
              text: '2 — 3 раза в месяц',
            },
            {
              id: 4,
              text: 'Вообще не общались',
            },
          ],
        },
        {
          id: 10,
          quest:
            'Как ты можешь охарактеризовать периодичность общения с наставником в третий месяц работы?',
          answers: [
            {
              id: 0,
              text: 'Каждый день',
            },
            {
              id: 1,
              text: '2 — 3 раза в неделю',
            },
            {
              id: 2,
              text: 'Один раз в неделю',
            },
            {
              id: 3,
              text: '2 — 3 раза в месяц',
            },
            {
              id: 4,
              text: 'Вообще не общались',
            },
          ],
        },
        {
          id: 11,
          quest:
            'Как строилось твое общение с наставником (оцени в процентном соотношении, когда ты был инициатором общения и когда он)?',
          answers: [
            {
              id: 0,
              text: '20% — 80%',
            },
            {
              id: 1,
              text: '30% — 70%',
            },
            {
              id: 2,
              text: '50% — 50%',
            },
            {
              id: 3,
              text: '70% — 30%',
            },
            {
              id: 4,
              text: '80% — 20%',
            },
          ],
        },
        {
          id: 12,
          quest:
            'Всегда ли наставник мог дать ответы на твои вопросы? Если не всегда, то на какие вопросы ты не получил ответ?',
          answers: [
            {
              id: 0,
              text: 'Всегда',
            },
            {
              id: 1,
              text: 'Часто',
            },
            {
              id: 2,
              text: 'Не всегда',
            },
          ],
        },
        {
          id: 13,
          quest:
            'Давал ли тебе наставник обратную связь по результатам работы? Говорил ли о том, что ты делаешь правильно, неправильно, что можно улучшить?',
          answers: [
            {
              id: 0,
              text: 'Да, каждый раз после освоения нового материала, вопроса.',
            },
            {
              id: 1,
              text: 'Да, раз в неделю подводил итог',
            },
            {
              id: 2,
              text: 'Да, раз в месяц.',
            },
            {
              id: 3,
              text: 'Да, но реже чем раз в месяц.',
            },
            {
              id: 4,
              text: 'Нет',
            },
            {
              id: 5,
              text: 'Свой вариант ответа',
            },
          ],
        },
        {
          id: 14,
          quest:
            'Как ты считаешь, какой ещё информацией должен был поделиться с тобой наставник, чтобы облегчить твое вхождение в должность?',
          answers: [],
        },
        {
          id: 15,
          quest:
            'Оцени по 5-балльной шкале работу своего наставника,5-наивысший балл (доступность, доброжелательность, структурированность, полнота обучения), если балл 3 и ниже, просьба указать, почему',
          answers: [
            {
              id: 0,
              text: '5',
            },
            {
              id: 1,
              text: '4',
            },
            {
              id: 2,
              text: '3',
            },
            {
              id: 3,
              text: '2',
            },
            {
              id: 4,
              text: '1',
            },
          ],
        },
        {
          id: 16,
          quest:
            'Был ли кто-то, кто помогал тебе с вхождением в должность помимо наставника? Какую помощь он тебе оказывал?',
          answers: [],
        },
        {
          id: 17,
          quest:
            'Была ли проведена промежуточная встреча с анализом твоих звонков в середине испытательного срока? ',
          answers: [
            {
              id: 0,
              text: 'Да',
            },
            {
              id: 1,
              text: 'Нет',
            },
          ],
        },
        {
          id: 18,
          quest:
            'Насколько эффективна она для тебя была? Какие выводы по итогам встречи ты для себя сделал?',
          answers: [
            {
              id: 0,
              text:
                'Очень эффективна, это помогло послушать себя со стороны, исправить ошибки и понять свои зоны роста в продажах.',
            },
            {
              id: 1,
              text:
                'Эффективно, я услышал себя со стороны, но в некоторых звонках критика была неконструктивна',
            },
            {
              id: 2,
              text: 'Не эффективно, критика была неконструктивна, встреча ничего не дала',
            },
            {
              id: 3,
              text: 'Другой вариант ответа',
            },
          ],
        },
      ],
    };
  }
}

export default QuestionData;
