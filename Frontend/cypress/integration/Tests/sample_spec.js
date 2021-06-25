describe("Testeo flujo de registro exitoso", () => {
	it("Visito la web de SmartWallet", () => {
		cy.visit("http://localhost:3000");
	});
	it("cambio el selector a registro", () => {
		cy.get("#Registro").click();
	});
	it("busco el input de nombre y escribo uno", () => {
		cy.get("#name")
			.type("Lucas")
			.should("have.value", "Lucas");
	});
	it("busco el input de email y escribo uno", () => {
		cy.get("#email")
			.type("marosolucas3@gmail.com")
			.should("have.value", "marosolucas3@gmail.com");
	});
	it("busco el input de contraseña y escribo uno", () => {
		cy.get("#password")
			.type("1")
			.should("have.value", "1");
	});
	it("Hago submit del form", () => {
		cy.get("form").submit();
	});
	it("Espero un toast", () => {
		cy.get("#toast-login");
	});
});