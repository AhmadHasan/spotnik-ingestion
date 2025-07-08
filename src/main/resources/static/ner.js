document.addEventListener("DOMContentLoaded", () => {
    const annotateButton = document.getElementById("annotateButton");
    const textInput = document.getElementById("textInput");
    const carouselInner = document.getElementById("carouselInner");

    annotateButton.addEventListener("click", async () => {
        const text = textInput.value.trim();
        if (!text) {
            alert("Please enter some text.");
            return;
        }

        try {
            const response = await fetch("/spotnik/api/spot", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ text }),
            });

            if (response.ok) {
                const data = await response.json();
                const entities = data.entities || [];
                carouselInner.innerHTML = ""; // Clear previous results
                entities.forEach(entity => {
                    const item = document.createElement("div");
                    item.className = "carousel-item";
                    item.innerHTML = `
                        <p><b>${entity.label}</b></p>
                        <p><a href="${entity.wikipediaUrl}" target="_blank">Wikipedia</a></p>
                        <p><img src="${entity.imageUrl}" alt="${entity.label}" /></p>
                        <p>${entity.caption}</p>
                    `;
                    carouselInner.appendChild(item);
                });
            } else {
                alert("Failed to retrieve entities.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred while retrieving entities.");
        }
    });
});