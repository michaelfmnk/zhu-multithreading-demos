async function simpleAsyncDemo() {
    // Simulating a long-running task
    const result = await new Promise(resolve => {
        setTimeout(() => resolve(123), 1000); // Wait for 1 second
    });
    console.log("Result:", result);
}

async function combiningAsyncOperations() {
    // Two async operations run in parallel
    const promise1 = new Promise(resolve => setTimeout(() => resolve("Hello"), 2000));
    const promise2 = new Promise(resolve => setTimeout(() => resolve("World"), 2000));

    // Wait for both promises to resolve
    const [result1, result2] = await Promise.all([promise1, promise2]);
    console.log(result1 + " " + result2);
}

async function exceptionHandlingInAsync() {
    try {
        const result = await new Promise((resolve, reject) => {
            if (Math.random() < 0.5) {
                reject(new Error("Something went wrong"));
            }
            resolve("Success");
        });
        console.log(result);
    } catch (error) {
        console.error("Failed due to", error.message);
    }
}

function thenDemo() {
    // Using .then() for a promise
    new Promise(resolve => {
        setTimeout(() => resolve("Data fetched with .then"), 1000);
    }).then(data => {
        console.log(data); // Handle the resolved value
    }).catch(error => {
        console.error("Error:", error); // Handle any errors
    });
}

async function main() {
    await simpleAsyncDemo();
    await combiningAsyncOperations();
    await exceptionHandlingInAsync();
    thenDemo(); // This function doesn't need to be awaited since it's not using async/await
}

main();
