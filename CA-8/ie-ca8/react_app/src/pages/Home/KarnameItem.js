import * as React from "react";
import HomeService from "../../services/HomeService";
import Translator from "../../utils/Translator";

export default class KarnameItem extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            course: null,
            notFound: false
        };

        this.getStatusStyle = this.getStatusStyle.bind(this);
    }

    componentDidMount() {
        this.getCourseById();
    }

    async getCourseById() {
        let course = await HomeService.getCourseById(this.props.grade.code);
        if (course == null) {
            this.setState({
                notFound: true
            });
        } else {
            this.setState({
                course: course
            });
        }
    }

    getStatusStyle(grade) {
        let style = "status-box ";

        if(grade >= 10)
            return style + "accepted";
        else if(grade < 10)
            return style + "not-accepted";
        else
            return style + "not-specified";
    }

    render() {
        return (
            <div className="h-100">
                {this.state.course !== null ?
                    this.renderPage()
                    :
                    this.renderLoading()
                }
            </div>
        );
    }

    renderLoading() {
        return (
            <div className="h-100 m-5 center-text p-5">
                <div className="spinner-grow text-danger m-5" role="status">
                    <span className="sr-only">Loading...</span>
                </div>
            </div>
        );
    }

    renderPage() {
        return (
            <div className="karname-item">
                <div className="row no-gutters">
                    <div className="col-1">
                        <div className="karname-index">
                            <span>
                                {this.props.index+1}
                            </span>
                        </div>
                    </div>
                    <div className="col-2">
                        <div className="karname-fields">
                            <span>{this.state.course.code}-{this.state.course.classCode}</span>
                        </div>
                    </div>
                    <div className="col-3">
                        <div className="karname-fields">
                            <span>{this.state.course.name}</span>
                        </div>
                    </div>
                    <div className="col-2">
                        <div className="karname-fields">
                            <span>{this.state.course.units} واحد</span>
                        </div>
                    </div>
                    <div className="col-2">
                        <div className="karname-status">
                            <div className={this.getStatusStyle(this.props.grade.grade)}>
                                <span>
                                    {Translator.toFa(this.getStatusStyle(this.props.grade.grade))}
                                </span>
                            </div>
                        </div>
                    </div>
                    <div className="col-2">
                        <div className="karname-field-green">
                            <span>{this.props.grade.grade}</span>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}