import * as React from "react";
import PropTypes, {string} from "prop-types";
import Translator from "../../utils/Translator";
import CoursesService from "../../services/CoursesService";

export default class Selecteditem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {

        }

        this.getStatusStyle = this.getStatusStyle.bind(this);
    }

    getStatusStyle(status) {
        let style = "status";
        switch (status) {
            case "finalized":
                return style + "-sabt-shode";
            case "non-finalized":
                return style + "-sabt-nahayi-nashode";
            case "pending":
                return style + "-dar-entezar";
        }
    }

    render() {
        return (
            <>
                <div className="col-1">
                    <div className="course-cell-">
                        <div className="course-delete-icon">
                            <i className="flaticon-trash-bin" style={{height: "100%"}}
                               onClick={() => this.props.onDelete(this.props.courseItem.offering.code,this.props.courseItem.offering.classCode)}>
                            </i>
                        </div>
                    </div>
                </div>
                <div className="col-3">
                    <div className="course-cell-">
                        <div className={this.getStatusStyle(this.props.courseItem.status)}>
                        <span>
                            {Translator.toFa(this.props.courseItem.status)}
                        </span>
                        </div>
                    </div>
                </div>
                <div className="col-2">
                    <div className="course-cell-">
                        <span>{this.props.courseItem.offering.code}-{this.props.courseItem.offering.classCode}</span>
                    </div>
                </div>
                <div className="col-3">
                    <div className="course-cell-">
                    <span>
                        {this.props.courseItem.offering.name}
                    </span>
                    </div>
                </div>
                <div className="col-2">
                    <div className="course-cell-">
                    <span>
                        {this.props.courseItem.offering.instructor}
                    </span>
                    </div>
                </div>
                <div className="col-1">
                    <div className="course-cell-" style={{borderLeft: "none", color: "blue"}}>
                    <span>
                        {this.props.courseItem.offering.units}
                    </span>
                    </div>
                </div>
            </>
        );
    }

}

Selecteditem.propTypes = {
    courseItem: PropTypes.shape({
        offering: PropTypes.shape({
            code: PropTypes.string,
            classCode: PropTypes.string,
            name: PropTypes.string,
            instructor: PropTypes.string,
            units: PropTypes.number,
        }),
        status: PropTypes.string,
    })
};


