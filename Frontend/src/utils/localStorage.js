export const readObject = key => {
	return JSON.parse(getKey(key) || "{}");
};
  
export const setObject = (key, value) => {
	return setKey(key, JSON.stringify(value));
};
  
export const getKey = key => localStorage.getItem(key);
  
export const setKey = (key, value) => localStorage.setItem(key, value);

export const deleteKey = (key) => localStorage.removeItem(key);
  