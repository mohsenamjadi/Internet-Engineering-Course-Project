import React from "react";
import "../../Assets/styles/footer.css";

export default class Header extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <footer>
                <div className="row no-gutters justify-content-center align-items-center footer-inner-box">
                    <div className="copy-right-icon m-2">
                        <i className="flaticon-copyright"></i>
                    </div>
                    <div className="copy-right-text ml-auto m-2">
                <span>
                    دانشگاه تهران - سامانه جامع بلبل‌ستان
                </span>

                    </div>
                    <div className="social-media-icons m-2">
                        <i className="flaticon-twitter-logo-on-black-background"></i>
                    </div>
                    <div className="social-media-icons m-2">
                        <i className="flaticon-instagram"></i>
                    </div>
                    <div className="social-media-icons m-2">
                        <i className="flaticon-linkedin-logo"></i>
                    </div>
                    <div className="social-media-icons m-2">
                        <i className="flaticon-facebook"></i>
                    </div>
                </div>
            </footer>
        );
    }
}