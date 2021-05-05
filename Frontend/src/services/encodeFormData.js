import querystring from "qs";

const encodeFormData = data => {
	return querystring.stringify(data, {
		arrayFormat: "repeat"
	});
};

export default encodeFormData;
