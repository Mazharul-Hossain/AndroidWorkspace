package nachos.threads;


/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>, and multiple
 * threads can be waiting to <i>listen</i>. But there should never be a time
 * when both a speaker and a listener are waiting, because the two threads can
 * be paired off at this point.
 */
public class Communicator {

	int value;

	/**
	 * Lock For This Communicator class
	 */
	Lock communicatorLock;

	/**
	 * True If a Thread is Speaking
	 */
	boolean isSpeaking;

	/**
	 * True if A thread is Listenning
	 */
	boolean isListenning;

	/**
	 * This condition along with boolean is Listenning allows only one thread to
	 * enter in listenning If a listener is listenning then listeners will sleep
	 * on this condition at the end of listening the listener will wake a thread
	 * sleeping on this condition
	 */
	Condition listennersCondition;

	/**
	 * If a Speaker is Speaking then Speaker will sleep on this condition at the
	 * end of Speaking the Speaker will wake a thread sleeping on this condition
	 */
	Condition speakersCondition;

	/*
	 * If a speaker comes First he will sleep(& wait) for a listenerIf a
	 * listener comes second it is his job to wake the speakerthrough this
	 * condition
	 */
	Condition waitForListener;

	/*
	*
	*/
	Condition waitForSpeaker;

	/*
	 * No matter what listner will ofcourse wait on this conditionSpeaker will
	 * wake listener aftaer genarating value
	 */
	Condition valueGenaration;

	/*
	 * No matter what speaker will ofcourse wait on this conditionListenner will
	 * wake Speaker aftaer receiving the value
	 */
	Condition valueReceived;

	boolean isSpeakerWaitingForListenner;
	boolean isListennerWaitingForSpeaker;

	/**
	 * Allocate a new communicator.
	 */
	public Communicator() {

		communicatorLock = new Lock();
		isSpeaking = false;
		isListenning = false;

		isSpeakerWaitingForListenner = false;
		isListennerWaitingForSpeaker = false;

		listennersCondition = new Condition(communicatorLock);
		speakersCondition = new Condition(communicatorLock);

		waitForListener = new Condition(communicatorLock);
		waitForSpeaker = new Condition(communicatorLock);

		valueGenaration = new Condition(communicatorLock);
		valueReceived = new Condition(communicatorLock);
	}

	/**
	 * Wait for a thread to listen through this communicator, and then transfer
	 * <i>word</i> to the listener.
	 * 
	 * <p>
	 * Does not return until this thread is paired up with a listening thread.
	 * Exactly one listener should receive <i>word</i>.
	 * 
	 * @param word
	 *            the integer to transfer.
	 */
	public void speak(int word) {

		communicatorLock.acquire();

		while (isSpeaking) {
			// A speaker is already speaking
			// let sleep untill the speaker stops speaking
			KThread currentThread = KThread.currentThread();

			// System.out.println( currentThread.getName() +
			// "Is Going To Sleep");

			speakersCondition.sleep();

			if (!communicatorLock.isHeldByCurrentThread()) {
				// a speaker wakes from sleep
				communicatorLock.acquire();
			}

		}

		if (!communicatorLock.isHeldByCurrentThread()) {
			// a speaker wakes from sleep
			communicatorLock.acquire();
		}

		isSpeaking = true;// a speaker starts speaking

		if (isListennerWaitingForSpeaker) {
			// a listenner arrived before a speaker
			// he is waitiing for the speaker in waight for speaker condition
			// let us generate a value and wait for his confirmation

			value = word;// value set
			KThread currentThread = KThread.currentThread();

			System.out.println(currentThread.getName() + " produced " + value);

			// wake the speaker for receiving the word
			valueGenaration.wake();

			// release the lock and wait untill listener receive the word
			valueReceived.sleep();

		} else {
			// speaker arrived first
			// still lock aquired
			// sleep untill a listenner arrived
			isSpeakerWaitingForListenner = true;

			waitForListener.sleep();

			if (!communicatorLock.isHeldByCurrentThread()) {
				// a speaker wakes from sleep
				communicatorLock.acquire();
			}

			isSpeakerWaitingForListenner = false;

			value = word;// value set

			// wake the speaker for receiving the word

			KThread currentThread = KThread.currentThread();
			System.out.println(currentThread.getName() + " produced " + value);
			valueGenaration.wake();

			// release the lock and wait untill listener receive the word
			valueReceived.sleep();

		}

		// speaking complete
		// wake another speaker for speaking
		if (!communicatorLock.isHeldByCurrentThread()) {

			communicatorLock.acquire();
		}
		isSpeaking = false;
		speakersCondition.wake();
		communicatorLock.release();

	}

	/**
	 * Wait for a thread to speak through this communicator, and then return the
	 * <i>word</i> that thread passed to <tt>speak()</tt>.
	 * 
	 * @return the integer transferred.
	 */
	public int listen() {

		int receivedValue;

		communicatorLock.acquire();
		while (isListenning) {
			// a listenner is already listenning
			// wait untill he finish
			listennersCondition.sleep();
			if (!communicatorLock.isHeldByCurrentThread()) {
				// a listenner wakes from sleep
				communicatorLock.acquire();
			}

		}

		if (!communicatorLock.isHeldByCurrentThread()) {
			// a listenner wakes from sleep
			communicatorLock.acquire();
		}

		isListenning = true;// a listenner

		if (isSpeakerWaitingForListenner) {
			// a speaker arrived already and waiting for listenner to
			// listen.
			waitForListener.wake();

			// listnner waiting untill a value is generated
			valueGenaration.sleep();

		} else {
			// listenner arrived first
			// still lock aquired

			isListennerWaitingForSpeaker = true;

			// listenner wait until a value is generated
			valueGenaration.sleep();

			isListennerWaitingForSpeaker = false;

		}

		// a value generated by the speaker
		if (!communicatorLock.isHeldByCurrentThread()) {

			communicatorLock.acquire();
		}

		receivedValue = value;

		valueReceived.wake();

		listennersCondition.wake();

		isListenning = false;

		communicatorLock.release();

		return receivedValue;

	}
}
