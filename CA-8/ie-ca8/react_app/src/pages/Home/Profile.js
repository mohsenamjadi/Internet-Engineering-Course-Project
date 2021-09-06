import * as React from "react";
import HomeService from "../../services/HomeService";

export default class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            GPA: 0,
            TotalPassedUnits: 0
        };
    }

    componentDidMount() {
        this.getStudentGPA();
        this.getStudentPassedUnits();
    }

    async getStudentGPA() {
        let gpa = await HomeService.getStudentGPA();
        console.log(gpa);
        this.setState(
            {
                GPA : gpa
            }
        )
    }

    async getStudentPassedUnits() {
        let units = await HomeService.getStudentPassedUnits();
        console.log(units);
        this.setState(
            {
                TotalPassedUnits : units
            }
        )
    }

    render() {
        return (
            <div>
                {this.props.student !== null ? this.renderPage() : this.renderLoading()}
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
            <div className="col-12">
                <div className="card cart-container">
                    <div className="profile-img">
                        <img style={{ borderRadius: "50%" }} alt="" src={this.props.student.img} />
                    </div>

                    <div className="row profile-info-text ">
                        <i> نام: </i> {this.props.student.name} {this.props.student.secondName}
                    </div>

                    <div className="row profile-info-text ">
                        <i> شماره دانشجویی: </i> {this.props.student.id}
                    </div>

                    <div className="row profile-info-text ">
                        <i> تاریخ تولد: </i> {this.props.student.birthDate}
                    </div>

                    <div className="row profile-info-text ">
                        <i> معدل کل: </i> {!isNaN(this.state.GPA) && this.state.GPA.toFixed(2)}
                    </div>

                    <div className="row profile-info-text ">
                        <i> واحد گذرانده: </i> {this.state.TotalPassedUnits}
                    </div>

                    <div className="row profile-info-text ">
                        <i> دانشکده: </i> {this.props.student.faculty}
                    </div>

                    <div className="row profile-info-text ">
                        <i> رشته: </i> {this.props.student.field}
                    </div>

                    <div className="row profile-info-text ">
                        <i> مقطع: </i> {this.props.student.level}
                    </div>

                    <div className="btn btn-outline-primary" style={{ pointerEvents: "none" }}>
                        مشغول به تحصیل
                    </div>
                </div>
            </div>
        );
    }
}