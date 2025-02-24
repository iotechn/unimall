export const validatePassword = () => {
	return (rule, value, callback) => {
		if (value.length < 6) {
			callback(new Error("密码不能少于6位"));
		} else {
			callback();
		}
	};
};

export const validateCode = () => {
	return (rule, value, callback) => {
		if (value.length < 4) {
			callback(new Error("验证码不能少于4位"));
		} else {
			callback();
		}
	};
};