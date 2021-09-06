import * as React from "react";
import PropTypes, {string} from "prop-types";
import Translator from "../../utils/Translator";
import "../../Assets/styles/courses.css";
import PlanService from "../../services/PlanService";
import CoursesSelected from "./CoursesSelected";
import CoursesService from "../../services/CoursesService";

export default class Presenteditem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
        }

        this.getStatusStyle = this.getStatusStyle.bind(this);
    }

    getStatusStyle(status) {
        let style = "status-";
        switch (status) {
            case "Umumi":
                return style + "omomi";
            case "Asli":
                return style + "darse-asli";
            case "Paaye":
                return style + "darse-paaye";
            case "Takhasosi":
                return style + "darse-takhasosi";
        }
    }

    ifPending(code, classCode) {
        if(this.props.selectedCourses == null || this.props.selectedCourses.length === 0)
            return false

        let weeklyScheduleItem = this.props.selectedCourses.
        find(weeklyScheduleItem => weeklyScheduleItem.offering.code == code && weeklyScheduleItem.offering.classCode== classCode);

        if(weeklyScheduleItem !== undefined && weeklyScheduleItem.status == "pending")
            return true

        return false
    }

    renderAdd(){
        return(
            <i className="flaticon-add" style={{height: '100%'}}
               onClick={() => this.props.onAdd(this.props.courseItem.code, this.props.courseItem.classCode)}>
            </i>
        );
    }

    renderPending(){
        return(
            <div className="course-listEntezar-icon" style={{height: '100%'}}>
                <i className="flaticon-clock-circular-outline"></i>
            </div>
        )
    }

    renderIcon(){
        if(this.ifPending(this.props.courseItem.code, this.props.courseItem.classCode))
            return(
                <div className="course-listEntezar-icon" style={{height: '100%'}}>
                    <i className="flaticon-clock-circular-outline"></i>
                </div>
            )
        else
            return(
                <i className="flaticon-add" style={{height: '100%'}}
                   onClick={() => this.props.onAdd(this.props.courseItem.code, this.props.courseItem.classCode)}>
                </i>
            );
    }

    render() {
        return (
            <>
                    <div className="col-1">
                        <div className="course-cell-">
                            <div className="course-add-icon">
                                {this.renderIcon()}
                            </div>
                        </div>
                    </div>
                    <div className="col-2">
                        <div className="course-cell-">
                            <span>{this.props.courseItem.code}-{this.props.courseItem.classCode}</span>
                        </div>
                    </div>
                    <div className="col-1">
                        <div className="course-cell-" style={{color: 'blue'}}>
                            <span>{this.props.courseItem.capacity}</span>
                        </div>
                    </div>
                    <div className="col-1">
                        <div className="course-cell-">
                            <div className={this.getStatusStyle(this.props.courseItem.type)}>
                                    <span>
                                        {Translator.toFa(this.props.courseItem.type)}
                                    </span>
                            </div>
                        </div>
                    </div>
                    <div className="col-2">
                        <div className="course-cell-">
                                <span>
                                    {this.props.courseItem.name}
                                </span>
                        </div>
                    </div>
                    <div className="col-2">
                        <div className="course-cell-">
                                <span>
                                    {this.props.courseItem.instructor}
                                </span>
                        </div>
                    </div>
                    <div className="col-1">
                        <div className="course-cell-" style={{color: 'blue'}}>
                                <span>
                                    {this.props.courseItem.units}
                                </span>
                        </div>
                    </div>
                    <div className="col-2">
                        <div className="course-cell-" style={{borderLeft: 'none'}}>
                            {this.props.courseItem.description}
                        </div>
                    </div>

            </>
        );
    }

}

Presenteditem.propTypes = {
    courseItem: PropTypes.shape({
        code: PropTypes.string,
        classCode: PropTypes.string,
        capacity: PropTypes.number,
        type: PropTypes.string,
        name: PropTypes.string,
        instructor: PropTypes.string,
        units: PropTypes.number,
        description: PropTypes.string
    })
};