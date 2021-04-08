const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();
const database = admin.firestore();

exports.resetDaily =
functions.pubsub.schedule("every day 00:00")
    .timeZone("Europe/Dublin")
    .onRun((context) => {
      functions.logger.log("Starting daily deletion");
      deleteCollection(database, "userDailys", 50);
      functions.logger.log("Complete daily deletion");
    });

/**
 * Delete a collection
 * @param {string} database - The database
 * @param {string} collectionPath - The collection path
 * @param {string} batchSize - The size of the batch
 * @return {Promise} promise - the promise to delete a batch
 */
async function deleteCollection(database, collectionPath, batchSize) {
  const collectionRef = database.collection(collectionPath);
  const query = collectionRef.orderBy("__name__").limit(batchSize);
  return new Promise((resolve, reject) => {
    deleteQueryBatch(database, query, resolve).catch(reject);
  });
}

/**
 * Delete a batch
 * @param {string} database - The database
 * @param {string} query - The collection
 * @param {string} resolve - resolve?
 */
async function deleteQueryBatch(database, query, resolve) {
  const snapshot = await query.get();
  const batchSize = snapshot.size;
  if (batchSize === 0) {
    resolve();
    return;
  }
  // Delete documents in a batchSize
  const batch = database.batch();
  snapshot.docs.forEach((doc) => {
    batch.delete(doc.ref);
  });
  await batch.commit();
  process.nextTick(() => {
    deleteQueryBatch(database, query, resolve);
  });
}
